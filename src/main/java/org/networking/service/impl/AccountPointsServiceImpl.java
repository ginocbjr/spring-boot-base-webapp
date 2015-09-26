package org.networking.service.impl;

import java.util.Date;
import java.util.List;

import org.networking.entity.AccountPoints;
import org.networking.entity.Member;
import org.networking.enums.PointType;
import org.networking.repository.AccountPointsRepository;
import org.networking.service.AccountPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AccountPointsServiceImpl extends BaseServiceImpl<AccountPoints> implements AccountPointsService {
	
	private AccountPointsRepository accountPointsRepository;

	@Override
	public AccountPoints create(AccountPoints accountPoints) {
		return accountPointsRepository.save(accountPoints);
	}

	@Override
	public List<AccountPoints> findAccountPointsByAccountAndDateAndType(Long accountId, Date date, PointType point) {
		return accountPointsRepository.findAccountPointsByAccountAndDateAndType(accountId, date, point);
	}
	
	@Override
	public List<AccountPoints> findAll() {
		return accountPointsRepository.findAll();
	}

	public void createByMemberAndType(Member member, PointType type) {

	}

	@Autowired
	@Override
	protected void setRepository(JpaRepository<AccountPoints, Long> repository) {
		this.repository = repository;
		accountPointsRepository = (AccountPointsRepository) repository;
	}
	
}