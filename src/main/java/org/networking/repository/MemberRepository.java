package org.networking.repository;

import java.util.Date;
import java.util.List;

import org.networking.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Sonny on 9/6/2015.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findMemberByUsername(String username);

	@Query("select m from Member m where m.firstName like :keyString or m.lastName like :keyString")
	List<Member> findByLastnameOrFirstnameLike(@Param(value = "keyString") String keyString);

	@Query("select m from Member m where m.id in " +
			"(select distinct a.memberId from Account a where a.id in " +
			"(select ap.accountId from AccountPoints ap where (ap.isClaimed is null or ap.isClaimed = false) and DATE(ap.createDate) = DATE(:date) ))")
	List<Member> findWithUnclaimed(@Param(value = "date")Date date);

	@Query("select m from Member m where m.id in " +
			"(select distinct a.memberId from Account a where a.id in " +
			"(select ap.accountId from AccountPoints ap where DATE(ap.createDate) = DATE(:date) ))")
	List<Member> findWithAccountPointsForDate(@Param(value = "date")Date date);

	@Query(value = "select u.id as memberId, u.FIRST_NAME as firstName, u.LAST_NAME as lastName, u.MIDDLE_NAME as middleName, t4.total_points as totalPoints, \n" +
			"case when eh.DATE_CLAIMED is null then false else true end as isClaimed\n" +
			"from User u join ( \n" +
			"select t3.member_id, sum(t3.total) total_points from (\n" +
			"select a.id, a.member_id, t2.total from account a\n" +
			"join (\n" +
			"select ap.ACCOUNT_ID, sum(ap.points) total from account_points ap\n" +
			"where date(ap.CREATEDATE) = date(:date)\n" +
			"group by ap.ACCOUNT_ID\n" +
			") t2 on t2.account_id = a.id) t3\n" +
			"group by t3.member_id) t4 on u.id = t4.member_id\n" +
			"left join earnings_history eh on eh.MEMBER_ID = u.id", nativeQuery = true)
	List<Object[]> findMemberEarningsByDate(@Param(value = "date")Date date);

	@Modifying
	@Query(value = "update account_points ap \n" +
			"set ap.IS_CLAIMED = true, ap.DATE_CLAIMED = now() \n" +
			"where ap.account_id in (select id from account where MEMBER_ID = :memberId) \n" +
			"and date(ap.CREATEDATE) = date(:date)", nativeQuery = true)
	void updateAccountPointsAsClaimed(@Param(value = "memberId") Long memberId, @Param(value = "date") Date date);
	
	@Query(value = "select concat(u.username,'-',@curRow \\:= @curRow + 1) as accountName, "
			+ " if(ap.point_type = 'REFERRAL', sum(ap.points),0) as referralPoints, "
			+ " if(ap.point_type = 'PRODUCT', sum(ap.points),0) as productPoints, "
			+ " if(ap.point_type = 'GROUP', sum(ap.points),0) as groupPoints "
			+ " from account_points ap "
			+ " join account a on ap.account_id = a.id "
			+ " join user u on a.member_id = u.id "
			+ " join (select @curRow \\:= 0) r "
			+ " where u.username = :username "
			+ " group by ap.account_id;", nativeQuery = true)
	List<Object[]> findAccountPointsByMember(@Param(value = "username") String username);
}
