package org.networking.service;

import org.networking.entity.Member;

/**
 * Created by Sonny on 9/6/2015.
 */
public interface MemberService extends BaseService<Member> {
	Member create(Member member);
}
