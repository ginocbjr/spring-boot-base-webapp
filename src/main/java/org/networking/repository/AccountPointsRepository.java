package org.networking.repository;

import org.networking.entity.AccountPoints;
import org.networking.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jona on 9/26/2015.
 */
public interface AccountPointsRepository extends JpaRepository<AccountPoints, Long> {
}
