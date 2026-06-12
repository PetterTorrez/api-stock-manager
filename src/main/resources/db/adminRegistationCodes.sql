CREATE TABLE admin_registration_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(36) NOT NULL UNIQUE,
    expires_at DATETIME NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    INDEX idx_code_lookup (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;