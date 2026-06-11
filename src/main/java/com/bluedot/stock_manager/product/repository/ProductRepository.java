package com.bluedot.stock_manager.product.repository;

import com.bluedot.stock_manager.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
