package com.bluedot.stock_manager.auth.service;

import com.bluedot.stock_manager.admin_code.service.AdminCodeService;
import com.bluedot.stock_manager.auth.dto.AuthRequestDTO;
import com.bluedot.stock_manager.auth.dto.AuthResponseDTO;
import com.bluedot.stock_manager.auth.dto.LoginRequestDTO;
import com.bluedot.stock_manager.auth.dto.LoginResponseDTO;
import com.bluedot.stock_manager.auth.mapper.AuthMapper;
import com.bluedot.stock_manager.auth.security.CustomUserDetails;
import com.bluedot.stock_manager.auth.security.JwtService;
import com.bluedot.stock_manager.user.model.User;
import com.bluedot.stock_manager.user.model.UserRole;
import com.bluedot.stock_manager.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final AuthMapper authMapper;
  private final PasswordEncoder passwordEncoder;
  private final AdminCodeService adminCodeService;
  private final JwtService jwtService;

  public AuthService(
    UserRepository userRepository,
    AuthMapper authMapper,
    PasswordEncoder passwordEncoder,
    AdminCodeService adminCodeService,
    AuthenticationManager authenticationManager,
    JwtService jwtService
  ) {
    this.userRepository = userRepository;
    this.authMapper = authMapper;
    this.passwordEncoder = passwordEncoder;
    this.adminCodeService = adminCodeService;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @Transactional
  public AuthResponseDTO registerUser(AuthRequestDTO dto) {
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
      throw new IllegalArgumentException(
        "Email already exists: " + dto.getEmail()
      );
    }

    User user = authMapper.toEntity(dto);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));

    if (
      dto.getCode() != null &&
      !dto.getCode().isBlank() &&
      adminCodeService.isAdminCodeValid(dto.getCode())
    ) {
      user.setRole(UserRole.ROLE_ADMIN);
    }

    User userSaved = userRepository.save(user);
    adminCodeService.markAsUsed(dto.getCode());

    return AuthResponseDTO.builder()
      .id(userSaved.getId())
      .name(userSaved.getName())
      .email(userSaved.getEmail())
      .role(userSaved.getRole())
      .build();
  }

  public LoginResponseDTO authenticate(LoginRequestDTO dto) {
    Authentication auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
    );

    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

    String roleName = userDetails
      .getAuthorities()
      .iterator()
      .next()
      .getAuthority();

    String token = jwtService.generateToken(
      userDetails.getId(),
      userDetails.getEmail(),
      roleName
    );

    return LoginResponseDTO.builder()
      .token(token)
      .email(userDetails.getEmail())
      .name(userDetails.getName())
      .role(roleName)
      .build();
  }
}
