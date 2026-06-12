package com.bluedot.stock_manager.audit.model;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit_logs")
public class AuditLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(nullable = false, length = 10)
  private String operation;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "previous_state", columnDefinition = "json")
  private JsonNode previousState;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "next_state", columnDefinition = "json")
  private JsonNode nextState;
}
