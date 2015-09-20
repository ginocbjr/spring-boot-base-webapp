package org.networking.repository;

import java.util.List;

import org.networking.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Sonny on 9/6/2015.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findMemberByUsername(String username);

	@Query("select m from Member m where m.firstName like :keyString or m.lastName like :keyString")
	List<Member> findByLastnameOrFirstnameLike(String keyString);
}
