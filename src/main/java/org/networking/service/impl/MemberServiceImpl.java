package org.networking.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.networking.entity.Account;
import org.networking.entity.AccountPoints;
import org.networking.entity.Member;
import org.networking.entity.User;
import org.networking.enums.PointType;
import org.networking.repository.AccountRepository;
import org.networking.repository.MemberRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Sonny on 9/6/2015.
 */
@Transactional
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member> implements MemberService {
	
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private MemberRepository memberRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountPointsService accountPointsService;

	@Override
	public Member create(Member member) {
		if(member.getNewPassword() != null) {
			member.setPassword(new BCryptPasswordEncoder().encode(member.getNewPassword()));
		}
		if(member.getTempRole() != null) {
			member.grantRole(User.Role.valueOf(member.getTempRole()));
		}
		
		try {
			if(member.getDateJoinedString() != null &&
					!member.getDateJoinedString().trim().isEmpty()) {
				member.setDateJoined(dateTimeFormat.parse(member.getDateJoinedString()));
			}
			
			if(member.getBirthdayString() == null ||
					member.getBirthdayString().trim().isEmpty()) {
				member.setBirthday(null);
			} else {
				member.setBirthday(dateFormat.parse(member.getBirthdayString()));
			}
		} catch (ParseException e) {
		}
		
		if(member.isNew()) {
			member.setCreateDate(new Date());
			member.setUpdateDate(new Date());
			
			// Add accounts
			Integer accounts = member.getNumOfAccounts();
			if(accounts != null && accounts >= 1) {
				for(int i = 1; i <= accounts; i++) {
					Account account = new Account();
					account.setCreateDate(new Date());
					account.setUpdateDate(new Date());
					account.setDateActivated(new Date());
					account.setMember(member);
					account.setTotalPoints(0d);
					if(i == 1) {
						account.setIsNext(true);
					} else {
						account.setIsNext(false);
					}
					accountRepository.save(account);
				}
			}
			
			
			if(member.getReferrer() != null && member.getReferrer().getId() != 1) {
				// Update referrers points
				List<Account> accountList = member.getReferrer().getAccounts();
				int listSize = accountList.size();
				int currentAccount = 0;
				for(Account acct : accountList) {
					// get next account
					if(acct.getIsNext()) {
						break; 
					}
					currentAccount++;
				}
				
				if(accountList != null && listSize >= 1) {
					for(int i = 1; i <= accounts; i++) {
						Account account = accountList.get(currentAccount);
						account.setTotalPoints(account.getTotalPoints() + 1);
						
						// If currentAccount is already equal to the index of the last account in the list, go back to zero
						// else continue iterating currentAccount
						if(currentAccount == listSize-1) {
							currentAccount = 0;
						} else {
							currentAccount++;
						}
						
						if(i == accounts) {
							Account next = accountList.get(currentAccount);
							next.setIsNext(true);
							accountRepository.save(next);
						} else if (accounts != 1) {
							account.setIsNext(false);
						}
						accountRepository.save(account);
						
						// Create/Update account points
						List<AccountPoints> accountPointsList = accountPointsService.findAccountPointsByAccountAndDateAndType(account.getId(), new Date(), PointType.REFERRAL);
						AccountPoints points = new AccountPoints();
						points.setUpdateDate(new Date());
						if(accountPointsList != null && accountPointsList.size() > 0) {
							points = accountPointsList.get(0);
							points.setPoints((long) (points.getPoints() + 1));
						} else {
							points.setCreateDate(new Date());
							points.setPoints((long) 1);
							points.setPointType(PointType.REFERRAL);
							points.setAccount(account);
						}
						accountPointsService.create(points);
					}
					
					// Create/Update group points
					List<AccountPoints> groupPointsList = accountPointsService.findAccountPointsByAccountAndDateAndType(1l, new Date(), PointType.GROUP);
					AccountPoints group = new AccountPoints();
					if(groupPointsList != null && groupPointsList.size() > 0) {
						group = groupPointsList.get(0);
						group.setPoints((long) (group.getPoints() + member.getNumOfAccounts()));
					} else {
						group.setCreateDate(new Date());
						group.setPoints((long) member.getNumOfAccounts());
						group.setPointType(PointType.GROUP);
						group.setAccount(accountRepository.findOne(1l));
					}
					accountPointsService.create(group);
				}
			}
		} else {
			member.setUpdateDate(new Date());
		}
		
        return memberRepository.save(member);
	}
	
	@Override
	public List<Member> findAll() {
		return memberRepository.findAll();
	}
	
	@Override
	public Member getMemberById(Long id) {
		return memberRepository.getOne(id);
	}
	
	@Override
	public List<Member> findMemberByUsername(String username) {
		return memberRepository.findMemberByUsername(username);
	}

	@Override
	public List<Member> findByLastnameOrFirstnameLike(String keyString){
		return memberRepository.findByLastnameOrFirstnameLike("%" + keyString + "%");
	}

	@Autowired
	@Override
	protected void setRepository(JpaRepository<Member, Long> repository) {
		this.repository = repository;
		memberRepository = (MemberRepository) repository;
	}
}