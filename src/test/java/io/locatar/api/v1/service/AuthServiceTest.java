package io.locatar.api.v1.service;

import io.locatar.api.v1.dto.MessageResponseDTO;
import io.locatar.api.v1.dto.UserLoginRequestDTO;
import io.locatar.api.v1.dto.UserLoginResponseDTO;
import io.locatar.api.v1.dto.UserRegisterRequestDTO;
import io.locatar.domain.user.UserEntity;
import io.locatar.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Should register a new user successfully")
    void register_ShouldSucceed_WhenValidData() {
        // Arrange
        UserRegisterRequestDTO dto = UserRegisterRequestDTO.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .passwordConfirm("password123")
                .build();

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        // Act
        MessageResponseDTO result = authService.register(dto);

        // Assert
        assertThat(result.getMessage()).isEqualTo("User registered successfully");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should throw exception when passwords do not match during registration")
    void register_ShouldThrowException_WhenPasswordsDoNotMatch() {
        // Arrange
        UserRegisterRequestDTO dto = UserRegisterRequestDTO.builder()
                .password("password123")
                .passwordConfirm("different")
                .build();

        // Act & Assert
        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Passwords do not match");
    }

    @Test
    @DisplayName("Should throw exception when username already exists")
    void register_ShouldThrowException_WhenUsernameExists() {
        // Arrange
        UserRegisterRequestDTO dto = UserRegisterRequestDTO.builder()
                .username("existing")
                .password("pass")
                .passwordConfirm("pass")
                .build();
        when(userRepository.existsByUsername("existing")).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> authService.register(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username already exists");
    }

    @Test
    @DisplayName("Should login successfully and return token")
    void login_ShouldReturnToken_WhenValidCredentials() {
        // Arrange
        UserLoginRequestDTO dto = new UserLoginRequestDTO("testuser", "password123");
        UserEntity user = UserEntity.builder()
                .username("testuser")
                .password("encodedPassword")
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        // Act
        UserLoginResponseDTO result = authService.login(dto);

        // Assert
        assertThat(result.getToken()).isEqualTo("jwt-token");
        assertThat(result.getTokenType()).isEqualTo("Bearer");
    }

    @Test
    @DisplayName("Should throw UNAUTHORIZED when user not found during login")
    void login_ShouldThrowUnauthorized_WhenUserNotFound() {
        // Arrange
        UserLoginRequestDTO dto = new UserLoginRequestDTO("nonexistent", "pass");
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("Should throw UNAUTHORIZED when password does not match")
    void login_ShouldThrowUnauthorized_WhenPasswordInvalid() {
        // Arrange
        UserLoginRequestDTO dto = new UserLoginRequestDTO("testuser", "wrongpass");
        UserEntity user = UserEntity.builder().username("testuser").password("encoded").build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encoded")).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authService.login(dto))
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.UNAUTHORIZED);
    }
}
