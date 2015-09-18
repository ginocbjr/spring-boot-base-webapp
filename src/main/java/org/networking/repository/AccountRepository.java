package org.networking.repository;

import org.networking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Sonny on 9/18/2015.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}