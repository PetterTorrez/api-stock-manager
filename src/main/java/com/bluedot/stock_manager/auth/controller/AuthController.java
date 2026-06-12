package com.bluedot.stock_manager.auth.controller;

import com.bluedot.stock_manager.auth.dto.AuthRequestDTO;
import com.bluedot.stock_manager.auth.dto.AuthResponseDTO;
import com.bluedot.stock_manager.auth.dto.LoginRequestDTO;
import com.bluedot.stock_manager.auth.dto.LoginResponseDTO;
import com.bluedot.stock_manager.auth.service.AuthService;
import com.bluedot.stock_manager.config.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthResponseDTO>> registerUser(
    @Valid @RequestBody AuthRequestDTO dto
  ) {
    AuthResponseDTO userResponse = this.authService.registerUser(dto);

    return ResponseEntity.ok(
      ApiResponse.<AuthResponseDTO>builder()
        .success(true)
        .message("User created successfully")
        .data(userResponse)
        .status(HttpStatus.OK.value())
        .build()
    );
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponseDTO>> authenticate(
    @RequestBody LoginRequestDTO dto
  ) {
    LoginResponseDTO responseDTO = this.authService.authenticate(dto);

    return ResponseEntity.ok(
      ApiResponse.<LoginResponseDTO>builder()
        .success(true)
        .message("User logged in successfully")
        .data(responseDTO)
        .status(HttpStatus.OK.value())
        .build()
    );
  }
}
