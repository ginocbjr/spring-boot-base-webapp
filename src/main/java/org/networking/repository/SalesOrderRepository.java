package org.networking.repository;

import org.networking.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gino on 9/21/2015.
 */
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}
