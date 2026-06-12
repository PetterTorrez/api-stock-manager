package com.bluedot.stock_manager.auth.service;

import com.bluedot.stock_manager.auth.security.CustomUserDetails;
import com.bluedot.stock_manager.user.model.User;
import com.bluedot.stock_manager.user.service.UserService;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    User user = userService.getUserByEmail(email);

    return CustomUserDetails.builder()
      .id(user.getId())
      .email(user.getEmail())
      .password(user.getPassword())
      .name(user.getName())
      .authorities(
        Collections.singletonList(
          new SimpleGrantedAuthority(user.getRole().name())
        )
      )
      .build();
  }
}
