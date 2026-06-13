package com.bluedot.stock_manager.admin_code.controller;

import com.bluedot.stock_manager.admin_code.service.AdminCodeService;
import com.bluedot.stock_manager.config.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-codes")
public class AdminCodeController {

  private final AdminCodeService adminCodeService;

  public AdminCodeController(AdminCodeService adminCodeService) {
    this.adminCodeService = adminCodeService;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/generate")
  public ResponseEntity<ApiResponse<String>> generateAdminCode() {
    String code = this.adminCodeService.createAdminCode();

    return ResponseEntity.ok(
      ApiResponse.<String>builder()
        .success(true)
        .message("Admin code generated successfully")
        .data(code)
        .status(HttpStatus.OK.value())
        .build()
    );
  }
}
