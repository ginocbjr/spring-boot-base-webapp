package org.networking.service.impl;

import org.networking.entity.Account;
import org.networking.entity.AccountPoints;
import org.networking.entity.Member;
import org.networking.enums.PointType;
import org.networking.repository.AccountPointsRepository;
import org.networking.service.AccountPointsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jona on 9/26/2015.
 */
@Transactional
@Service
public class AccoutPointsServiceImpl extends BaseServiceImpl<AccountPoints> implements AccountPointsService {

    private AccountPointsRepository accountPointsRepository;

    @Override
    public void createByMemberAndType(Member member, PointType type) {
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
       /* if(accountList != null && listSize >= 1) {
            for(int i = 1; i <= accounts; i++) {
                Account account = accountList.get(currentAccount);
                account.setTotalPoints(account.getTotalPoints() + 1);

                AccountPoints points = new AccountPoints(account, type, );




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
            }
        }
    */

    }

    @Override
    protected void setRepository(JpaRepository<AccountPoints, Long> repository) {
        this.repository = repository;
        accountPointsRepository = (AccountPointsRepository) repository;
    }
}
