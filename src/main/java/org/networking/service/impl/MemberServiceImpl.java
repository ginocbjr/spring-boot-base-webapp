package org.networking.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.networking.entity.Member;
import org.networking.entity.User;
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