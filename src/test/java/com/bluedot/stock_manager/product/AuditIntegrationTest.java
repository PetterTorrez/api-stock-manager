package com.bluedot.stock_manager.product;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bluedot.stock_manager.audit.repository.AuditLogRepository;
import com.bluedot.stock_manager.auth.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuditIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuditLogRepository auditLogRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void createProduct_ShouldSucceedAndTriggerAuditLog_WhenUserIsAdmin()
    throws Exception {
    auditLogRepository.deleteAll();

    String adminToken = jwtService.generateToken(
      9L,
      "admin@gmail.com",
      "ROLE_ADMIN"
    );

    Map<String, Object> productPayload = new HashMap<>();
    productPayload.put("sku", "PROD-MX-04");
    productPayload.put("name", "Macbook M2 Pro");
    productPayload.put("price", 28000.00);
    productPayload.put("description", "13 inch 512GB SSD 16GB RAM");

    mockMvc
      .perform(
        post("/products")
          .header("Authorization", "Bearer " + adminToken)
          .header("X-Correlation-ID", "TEST-TRACE-12345")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(productPayload))
      )
      .andExpect(status().isOk());

    long totalAuditLogs = auditLogRepository.count();
    assertTrue(totalAuditLogs > 0, "Audit log must be added");
  }
}
