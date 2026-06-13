package com.bluedot.stock_manager.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDTO {

  private Long id;
  private String sku;
  private String name;
  private double price;
  private String description;
}
