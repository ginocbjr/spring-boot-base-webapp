package org.networking.service;

import java.util.List;

import org.networking.entity.Member;

/**
 * Created by Sonny on 9/6/2015.
 */
public interface MemberService extends BaseService<Member> {
	Member create(Member member);

	List<Member> findAll();
	
	Member getMemberById(Long id);
	
	List<Member> findMemberByUsername(String username);
}
