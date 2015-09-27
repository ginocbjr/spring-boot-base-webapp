package org.networking.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.networking.entity.Account;
import org.networking.entity.Member;
import org.networking.entity.MemberEarning;
import org.networking.entity.PointsSummaryHelper;
import org.networking.entity.User;
import org.networking.repository.AccountRepository;
import org.networking.repository.MemberRepository;
import org.networking.service.AccountPointsService;
import org.networking.service.EarningsHistoryService;
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

	@Autowired
	private EarningsHistoryService earningsHistoryService;

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

		// Add accounts
		Integer accounts = member.getNumOfAccounts();

		if(member.isNew()) {
			member.setCreateDate(new Date());
			member.setUpdateDate(new Date());

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
				//Add points to referrer
				accountPointsService.createForReferral(member, accounts);
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

	@Override
	public List<Member> findWithUnclaimed(Date date) {
		return memberRepository.findWithUnclaimed(date);
	}

	@Override
	public List<MemberEarning> findMemberEarningsByDate(Date date) {
		List<MemberEarning> earnings = new ArrayList<>();
		List<Object[]> objs = memberRepository.findMemberEarningsByDate(date);
		for(Object[] obj : objs) {
			MemberEarning me = new MemberEarning();
			me.setMemberId(((BigInteger)obj[0]).longValue());
			me.setFirstName((String)obj[1]);
			me.setLastName((String)obj[2]);
			me.setMiddleName((String)obj[3]);
			me.setTotalPoints(((BigDecimal)obj[4]).longValue());
			BigInteger bg = (BigInteger)obj[5];
			if(bg != null && bg.intValue() == 1) {
				me.setIsClaimed(true);
			} else {
				me.setIsClaimed(false);
			}
			earnings.add(me);
		}
		return earnings;
	}
	
	@Override
	public List<PointsSummaryHelper> findAccountPointsByMember(String username) {
		List<PointsSummaryHelper> summary = new ArrayList<>();
		List<Object[]> objs = memberRepository.findAccountPointsByMember(username);
		for(Object[] obj : objs) {
			PointsSummaryHelper p = new PointsSummaryHelper();
			p.setAccountName((String)obj[0]);
			p.setPersonalPoints(((BigDecimal)obj[1]).longValue());
			p.setReferralPoints(((BigDecimal)obj[2]).longValue());
			p.setProductPoints(((BigDecimal)obj[3]).longValue());
			p.setGroupPoints(((BigDecimal)obj[4]).longValue());
			summary.add(p);
		}
		return summary;
	}

	@Override
	public void markEarningsAsClaimed(Long memberId, Long totalPoints, Double totalEarnings, Date date) {
		memberRepository.updateAccountPointsAsClaimed(memberId, date);
		Member member = memberRepository.findOne(memberId);
		earningsHistoryService.createEarningsHistory(member, totalPoints, totalEarnings);
	}

	@Autowired
	@Override
	protected void setRepository(JpaRepository<Member, Long> repository) {
		this.repository = repository;
		memberRepository = (MemberRepository) repository;
	}
}