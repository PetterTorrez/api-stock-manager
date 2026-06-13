package com.bluedot.stock_manager.product.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDTO {

  private Long id;

  @NotBlank(message = "SKU is required")
  @Size(min = 6, max = 15, message = "SKU must be between 6 and 15 characters")
  private String sku;

  @NotBlank(message = "Name is required")
  @Size(
    min = 6,
    max = 150,
    message = "Name must be between 6 and 150 characters"
  )
  private String name;

  @NotNull(message = "Price is required ")
  @Positive(message = "Price must be greater than zero")
  @DecimalMax(
    value = "999999.99",
    message = "Price exceeds maximum allowed value"
  )
  private Double price;

  @Size(max = 900, message = "Description must be less than 900 characters")
  private String description;
}
