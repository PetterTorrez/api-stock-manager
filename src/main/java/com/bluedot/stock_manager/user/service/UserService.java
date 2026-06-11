package com.bluedot.stock_manager.user.service;

import com.bluedot.stock_manager.user.model.User;
import com.bluedot.stock_manager.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByEmail(String email) {
    return userRepository
      .findByEmail(email)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }
}
