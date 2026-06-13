package com.bluedot.stock_manager.admin_code.repository;

import com.bluedot.stock_manager.admin_code.model.AdminCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCodeRepository extends JpaRepository<AdminCode, Long> {
  Optional<AdminCode> findByCode(String code);
}
