CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    operation VARCHAR(10) NOT NULL,
    timestamp DATETIME NOT NULL,
    previous_state JSON DEFAULT NULL,
    next_state JSON DEFAULT NULL,
    INDEX idx_audit_timestamp (timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;