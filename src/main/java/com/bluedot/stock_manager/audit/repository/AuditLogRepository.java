package com.bluedot.stock_manager.audit.repository;

import com.bluedot.stock_manager.audit.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
