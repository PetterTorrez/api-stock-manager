package com.bluedot.stock_manager.user.controller;

import com.bluedot.stock_manager.user.model.User;
import com.bluedot.stock_manager.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/by-email")
  public User getUserByEmail(@RequestBody String email) {
    return userService.getUserByEmail(email);
  }
}
