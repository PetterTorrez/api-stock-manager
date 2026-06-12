package com.bluedot.stock_manager.admin_code.service;

import com.bluedot.stock_manager.admin_code.model.AdminCode;
import com.bluedot.stock_manager.admin_code.repository.AdminCodeRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AdminCodeService {

  private final AdminCodeRepository adminCodeRepository;

  public AdminCodeService(AdminCodeRepository adminCodeRepository) {
    this.adminCodeRepository = adminCodeRepository;
  }

  @Transactional
  public String createAdminCode() {
    LocalDateTime time = LocalDateTime.now();

    AdminCode code = new AdminCode();
    code.setCode(UUID.randomUUID().toString());
    code.setUsed(false);
    code.setExpiresAt(time.plusDays(1));

    this.adminCodeRepository.save(code);

    return code.getCode();
  }

  public Boolean isAdminCodeValid(String code) {
    AdminCode adminCode = this.adminCodeRepository.findByCode(code).orElseThrow(
      () -> new IllegalArgumentException("Admin code not found")
    );

    if (
      adminCode.isUsed() ||
      adminCode.getExpiresAt().isBefore(LocalDateTime.now())
    ) {
      throw new IllegalArgumentException("Code already used or expired");
    }

    return true;
  }

  @Transactional
  public void markAsUsed(String code) {
    AdminCode adminCode = this.adminCodeRepository.findByCode(code).orElse(
      null
    );

    if (adminCode != null) {
      adminCode.setUsed(true);
      this.adminCodeRepository.save(adminCode);
    }
  }
}
