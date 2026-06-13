package com.bluedot.stock_manager.product.mapper;

import com.bluedot.stock_manager.product.dto.ProductRequestDTO;
import com.bluedot.stock_manager.product.dto.ProductResponseDTO;
import com.bluedot.stock_manager.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductResponseDTO toResponseDTO(Product product) {
    return ProductResponseDTO.builder()
      .id(product.getId())
      .sku(product.getSku())
      .name(product.getName())
      .price(product.getPrice())
      .description(product.getDescription())
      .build();
  }

  public Product toEntity(ProductRequestDTO dto) {
    Product product = new Product();
    if (dto.getId() != null) {
      product.setId(dto.getId());
    }
    product.setSku(dto.getSku());
    product.setName(dto.getName());
    product.setPrice(dto.getPrice());
    product.setDescription(dto.getDescription());

    return product;
  }

  public Product updateEntity(Product product, ProductRequestDTO dto) {
    if (dto.getName() != null) {
      product.setName(dto.getName());
    }

    if (dto.getPrice() != null) {
      product.setPrice(dto.getPrice());
    }

    if (dto.getDescription() != null) {
      product.setDescription(dto.getDescription());
    }

    return product;
  }
}
