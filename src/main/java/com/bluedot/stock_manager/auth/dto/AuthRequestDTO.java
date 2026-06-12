package com.bluedot.stock_manager.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDTO {

  @NotBlank(message = "Name is required")
  @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  @Size(
    min = 6,
    max = 100,
    message = "Email must be between 6 and 100 characters"
  )
  private String email;

  @NotBlank(message = "Password is required")
  @Size(
    min = 6,
    max = 60,
    message = "Password must be between 6 and 60 characters"
  )
  private String password;

  private String code;
}
