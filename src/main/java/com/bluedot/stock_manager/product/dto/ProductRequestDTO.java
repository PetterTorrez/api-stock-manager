package com.bluedot.stock_manager.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDTO {

  private Long id;

  @NotBlank(message = "SKU is required")
  @Size(min = 6, max = 10, message = "SKU must be between 6 and 10 characters")
  private String sku;

  @NotBlank(message = "Name is required")
  @Size(
    min = 10,
    max = 150,
    message = "Name must be between 10 and 150 characters"
  )
  private String name;

  @NotBlank(message = "Price is required")
  private Double price;

  @Size(max = 900, message = "Description must be less than 900 characters")
  private String description;
}
