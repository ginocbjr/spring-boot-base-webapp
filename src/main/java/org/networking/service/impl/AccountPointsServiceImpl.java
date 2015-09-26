package org.networking.service.impl;

import java.util.Date;
import java.util.List;

import org.networking.entity.*;
import org.networking.enums.PointType;
import org.networking.repository.AccountPointsRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.AccountService;
import org.networking.service.SalesOrderService;
import org.networking.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountPointsServiceImpl extends BaseServiceImpl<AccountPoints> implements AccountPointsService {

	private AccountPointsRepository accountPointsRepository;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SalesOrderService salesOrderService;
	
	@Override
	public AccountPoints create(AccountPoints accountPoints) {
		return accountPointsRepository.save(accountPoints);
	}

	@Override
	public List<AccountPoints> findAccountPointsByAccountAndDateAndType(Long accountId, Date date, PointType point) {
		return accountPointsRepository.findAccountPointsByAccountAndDateAndType(accountId, date, point);
	}
	
	@Override
	public List<AccountPoints> findAll() {
		return accountPointsRepository.findAll();
	}

	@Override
	public void createForReferral(Member referrer, Integer newAcctCount){
		// Update referrers points
		Long referralPoints = settingsService.findByKey(Settings.SETTINGS_TOTAL_POINTS_PER_REFERRAL).getNumberValue();
		Long memberPercentage = settingsService.findByKey(Settings.SETTINGS_PERCENTAGE_PER_REFERRAL).getNumberValue();

		//Points to be distributed depends on Settings
		Long totalPointsForDistribution
				= (newAcctCount * referralPoints) * (memberPercentage/100);
		Long groupPoints = ((newAcctCount * referralPoints) -  totalPointsForDistribution);
		Double totalGroupPoints = groupPoints.doubleValue();

		this.distributePoints(PointType.REFERRAL, referrer, totalPointsForDistribution, totalGroupPoints);

	}

	@Override
	public void createForProduct(SalesOrder order){
		salesOrderService.setPoints(order);

		this.distributePoints(PointType.PRODUCT, order.getSeller(),
				order.getTotalMemberPoints(), order.getTotalGroupPoints());
	}

	public void distributePoints(PointType type, Member member, Long totalPointsForDistribution, Double totalGroupPoints){
		List<Account> accountList = member.getReferrer().getAccounts();

		int accountSize = accountList.size();
		int currentAccount = 0;
		for(Account acct : accountList) {
			// get next account
			if(acct.getIsNext()) {
				break;
			}
			currentAccount++;
		}

		if(accountList != null &&  accountSize >= 1) {
			for(int i = 1; i <= totalPointsForDistribution; i++) {
				Account account = accountList.get(currentAccount);
				account.setTotalPoints(account.getTotalPoints() + 1);

				// If currentAccount is already equal to the index of the last account in the list, go back to zero
				// else continue iterating currentAccount
				if(currentAccount ==  accountSize-1) {
					currentAccount = 0;
				} else {
					currentAccount++;
				}

				if(i == totalPointsForDistribution) {
					Account next = accountList.get(currentAccount);
					next.setIsNext(true);
					accountService.save(next);
				} else{
					if(accountSize > 1){
						account.setIsNext(false);
					}
				}
				accountService.save(account);

				// Create/Update account points
				List<AccountPoints> accountPointsList = this.findAccountPointsByAccountAndDateAndType(account.getId(), new Date(), type);
				AccountPoints points = new AccountPoints();
				points.setUpdateDate(new Date());
				if(accountPointsList != null && accountPointsList.size() > 0) {
					points = accountPointsList.get(0);
					points.setPoints((points.getPoints() + 1));
				} else {
					points.setCreateDate(new Date());
					points.setPoints(1D);
					points.setPointType(type);
					points.setAccount(account);
				}
				this.create(points);
			}

			// Create/Update group points
			List<AccountPoints> groupPointsList = this.findAccountPointsByAccountAndDateAndType(1l, new Date(), type);
			AccountPoints group = new AccountPoints();
			if(groupPointsList != null && groupPointsList.size() > 0) {
				group = groupPointsList.get(0);
				group.setPoints(group.getPoints() + totalGroupPoints);
			} else {
				group.setCreateDate(new Date());
				group.setPoints(totalGroupPoints);
				group.setPointType(type);
				group.setAccount(accountService.load(1l));
			}
			this.create(group);
		}
	}

	@Autowired
	@Override
	protected void setRepository(JpaRepository<AccountPoints, Long> repository) {
		this.repository = repository;
		accountPointsRepository = (AccountPointsRepository) repository;
	}
	
}