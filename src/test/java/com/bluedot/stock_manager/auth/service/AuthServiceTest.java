package com.bluedot.stock_manager.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bluedot.stock_manager.admin_code.service.AdminCodeService;
import com.bluedot.stock_manager.auth.dto.AuthRequestDTO;
import com.bluedot.stock_manager.auth.dto.AuthResponseDTO;
import com.bluedot.stock_manager.auth.mapper.AuthMapper;
import com.bluedot.stock_manager.user.model.User;
import com.bluedot.stock_manager.user.model.UserRole;
import com.bluedot.stock_manager.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthMapper authMapper;

  @Mock
  private AdminCodeService adminCodeService;

  @InjectMocks
  private AuthService authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createUser_ShouldHashPasswordAndForceCustomerRole_WhenRequestIsNormal() {
    AuthRequestDTO request = AuthRequestDTO.builder()
      .name("Test User")
      .email("test@example.com")
      .password("plainPassword123")
      .build();

    User userDummy = new User();
    userDummy.setName("Usuario Reto");
    userDummy.setEmail("reto@example.com");

    User savedUser = new User();
    savedUser.setId(1L);
    savedUser.setName("Test User");
    savedUser.setEmail("test@example.com");
    savedUser.setPassword("$2a$10$hashedPasswordBcrypted");
    savedUser.setRole(UserRole.ROLE_ANONYMOUS);

    when(authMapper.toEntity(any(AuthRequestDTO.class))).thenReturn(userDummy);

    when(userRepository.findByEmail(request.getEmail())).thenReturn(
      Optional.empty()
    );
    when(passwordEncoder.encode(request.getPassword())).thenReturn(
      "$2a$10$hashedPasswordBcrypted"
    );
    when(userRepository.save(any(User.class))).thenReturn(savedUser);

    AuthResponseDTO response = authService.registerUser(request);

    assertNotNull(response);
    assertEquals(UserRole.ROLE_ANONYMOUS, response.getRole());
    assertEquals("test@example.com", response.getEmail());

    verify(passwordEncoder, times(1)).encode("plainPassword123");
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void createUser_ShouldThrowException_WhenEmailAlreadyExists() {
    AuthRequestDTO request = AuthRequestDTO.builder()
      .email("duplicate@example.com")
      .build();

    when(userRepository.findByEmail(request.getEmail())).thenReturn(
      Optional.of(new User())
    );

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      authService.registerUser(request);
    });

    assertEquals(
      "Email already exists: duplicate@example.com",
      exception.getMessage()
    );
    verify(userRepository, never()).save(any(User.class));
  }
}
