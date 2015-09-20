package org.networking.repository;

import org.networking.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Gino on 9/20/2015.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
