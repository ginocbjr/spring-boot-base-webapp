package org.networking.service.impl;

import org.networking.entity.SalesOrder;
import org.networking.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gino on 9/21/2015.
 */
@Service
@Transactional
public class SalesOrderServiceImpl extends BaseServiceImpl<SalesOrder> implements SalesOrderService {

    @Autowired
    @Override
    protected void setRepository(JpaRepository<SalesOrder, Long> repository) {
        this.repository = repository;
    }
}
