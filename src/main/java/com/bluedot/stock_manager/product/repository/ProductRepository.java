package com.bluedot.stock_manager.product.repository;

import com.bluedot.stock_manager.product.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findBySku(String sku);
}
