package com.bluedot.stock_manager.audit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuditLogEvent {

  private final Long userId;
  private final String operation;
  private final Object previousState;
  private final Object nextState;
}
