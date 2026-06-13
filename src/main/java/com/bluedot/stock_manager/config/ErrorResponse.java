package com.bluedot.stock_manager.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

  private final boolean success = false;
  private String error;
  private String message;
}
