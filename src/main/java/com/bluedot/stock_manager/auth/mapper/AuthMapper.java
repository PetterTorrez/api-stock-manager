package com.bluedot.stock_manager.auth.mapper;

import com.bluedot.stock_manager.auth.dto.AuthRequestDTO;
import com.bluedot.stock_manager.user.model.User;
import com.bluedot.stock_manager.user.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

  public User toEntity(AuthRequestDTO dto) {
    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    user.setRole(UserRole.ROLE_ANONYMOUS);

    return user;
  }
}
