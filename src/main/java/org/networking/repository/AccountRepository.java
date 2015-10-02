package org.networking.repository;

import java.util.List;

import org.networking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Sonny on 9/18/2015.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	@Query(value="select * from ACCOUNT a where a.CREATEDATE < date(now())",nativeQuery = true)
	List<Account> getActiveAccountsToday();
	
	@Query(value="select * from ACCOUNT a where a.IS_NEXT_FOR_GROUP = 1",nativeQuery = true)
	List<Account> getNextAccountForGroupPoints();
	
	@Query(value="select sum(ap.points) from ACCOUNT_POINTS ap "
			+ " where ap.account_id in (select a.id from ACCOUNT a where a.member_id = 1) "
			+ " and date(ap.createdate) = date(now())",nativeQuery = true)
	Long totalPointsForDistribution();
}