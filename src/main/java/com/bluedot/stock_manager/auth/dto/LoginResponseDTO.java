package com.bluedot.stock_manager.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

  private String name;
  private String email;
  private String token;
  private String role;
}
