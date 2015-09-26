package org.networking.repository;

import java.util.Date;
import java.util.List;

import org.networking.entity.AccountPoints;
import org.networking.enums.PointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Sonny on 9/26/2015.
 */
public interface AccountPointsRepository extends JpaRepository<AccountPoints, Long> {
	@Query("select a from AccountPoints a where a.account.id = :accountId and date(a.createDate) = date(:date) and a.pointType = :type")
	List<AccountPoints> findAccountPointsByAccountAndDateAndType(@Param(value = "accountId") Long accountId, @Param(value = "date") Date date,
			@Param(value = "type") PointType type);
}