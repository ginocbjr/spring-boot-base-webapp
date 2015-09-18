package org.networking.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.networking.entity.Account;
import org.networking.entity.Member;
import org.networking.entity.User;
import org.networking.repository.AccountRepository;
import org.networking.repository.MemberRepository;
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
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Member create(Member member) {
		member.setPassword(new BCryptPasswordEncoder().encode(member.getNewPassword()));
		if(member.getTempRole() != null) {
			member.grantRole(User.Role.valueOf(member.getTempRole()));
		}
		member.setCreateDate(new Date());
		member.setUpdateDate(new Date());
		if(member.getBirthdayString() == null ||
				member.getBirthdayString().trim().isEmpty()) {
			member.setBirthday(null);
		} else {
			try {
				member.setDateJoined(dateTimeFormat.parse(member.getDateJoinedString()));
				member.setBirthday(dateFormat.parse(member.getBirthdayString()));
			} catch (ParseException e) {
			}
		}
		
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
				double a = account.getTotalPoints() + 1;
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
				}
				
				account.setIsNext(false);
				accountRepository.save(account);
			}
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
	protected void setRepository(JpaRepository<Member, Long> repository) {
		this.memberRepository = (MemberRepository) repository;
	}
}