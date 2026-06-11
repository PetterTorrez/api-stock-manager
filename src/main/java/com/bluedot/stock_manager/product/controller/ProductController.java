package com.bluedot.stock_manager.product.controller;

import com.bluedot.stock_manager.product.model.Product;
import com.bluedot.stock_manager.product.service.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/all")
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }
}
