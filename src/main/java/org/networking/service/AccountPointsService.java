package org.networking.service;

import org.networking.entity.AccountPoints;
import org.networking.entity.Member;
import org.networking.enums.PointType;

import java.util.List;

/**
 * Created by Jona on 9/26/2015.
 */
public interface AccountPointsService extends BaseService<AccountPoints> {

	void createByMemberAndType(Member member, PointType type);
}
