package com.bluedot.stock_manager.auth.dto;

import com.bluedot.stock_manager.user.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

  private Long id;
  private String name;
  private String email;
  private UserRole role;
}
