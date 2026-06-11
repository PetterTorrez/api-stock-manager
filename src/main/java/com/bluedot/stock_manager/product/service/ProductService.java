package com.bluedot.stock_manager.product.service;

import com.bluedot.stock_manager.product.model.Product;
import com.bluedot.stock_manager.product.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }
}
