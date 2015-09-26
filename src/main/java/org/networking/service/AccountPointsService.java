package org.networking.service;

import java.util.Date;
import java.util.List;

import org.networking.entity.AccountPoints;
import org.networking.entity.Member;
import org.networking.enums.PointType;

/**
 * Created by Sonny on 9/26/2015.
 */
public interface AccountPointsService extends BaseService<AccountPoints> {
	AccountPoints create(AccountPoints accountPoints);

	List<AccountPoints> findAccountPointsByAccountAndDateAndType(Long accountId, Date date, PointType type);

	List<AccountPoints> findAll();

	void createByMemberAndType(Member member, PointType type);
}

