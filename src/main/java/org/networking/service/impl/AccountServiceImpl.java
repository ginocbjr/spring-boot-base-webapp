package org.networking.service.impl;

import org.networking.entity.*;
import org.networking.service.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jona on 9/26/2015.
 */
@Transactional
@Service
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {

	@Override
	protected void setRepository(JpaRepository<Account, Long> repository) {
		this.repository = repository;
	}

}