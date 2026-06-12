package com.bluedot.stock_manager.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

  private int status;
  private boolean success;
  private String message;
  private T data;
}
