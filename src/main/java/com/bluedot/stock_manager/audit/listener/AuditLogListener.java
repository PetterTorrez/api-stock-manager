package com.bluedot.stock_manager.audit.listener;

import com.bluedot.stock_manager.audit.model.AuditLog;
import com.bluedot.stock_manager.audit.model.AuditLogEvent;
import com.bluedot.stock_manager.audit.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditLogListener {

  private final AuditLogRepository auditLogRepository;
  private final ObjectMapper objectMapper;

  public AuditLogListener(
    AuditLogRepository auditLogRepository,
    ObjectMapper objectMapper
  ) {
    this.auditLogRepository = auditLogRepository;
    this.objectMapper = objectMapper;
  }

  @EventListener
  public void handleProductAuditEvent(AuditLogEvent event) {
    try {
      AuditLog log = new AuditLog();
      log.setUserId(event.getUserId());
      log.setOperation(event.getOperation());
      log.setTimestamp(LocalDateTime.now());

      // valueToTree para poder manejar los json con mysql
      log.setPreviousState(
        event.getPreviousState() != null
          ? objectMapper.valueToTree(event.getPreviousState())
          : null
      );
      log.setNextState(
        event.getNextState() != null
          ? objectMapper.valueToTree(event.getNextState())
          : null
      );

      auditLogRepository.save(log);
    } catch (Exception e) {
      log.info("Error saving audit log: " + e.getMessage());
    }
  }
}
