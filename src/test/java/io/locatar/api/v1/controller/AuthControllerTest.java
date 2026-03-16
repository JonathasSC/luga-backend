package io.locatar.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.locatar.api.v1.dto.MessageResponseDTO;
import io.locatar.api.v1.dto.UserLoginRequestDTO;
import io.locatar.api.v1.dto.UserLoginResponseDTO;
import io.locatar.api.v1.dto.UserRegisterRequestDTO;
import io.locatar.api.v1.service.AuthService;
import io.locatar.api.v1.service.JwtService;
import io.locatar.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should register a new user")
    void register_ShouldReturnCreated() throws Exception {
        // Arrange
        UserRegisterRequestDTO dto = UserRegisterRequestDTO.builder()
                .username("newuser")
                .email("new@example.com")
                .password("pass123")
                .passwordConfirm("pass123")
                .build();
        MessageResponseDTO response = MessageResponseDTO.builder().message("User registered successfully").build();
        when(authService.register(any(UserRegisterRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"));

        verify(authService, times(1)).register(any(UserRegisterRequestDTO.class));
    }

    @Test
    @DisplayName("Should login successfully")
    void login_ShouldReturnToken() throws Exception {
        // Arrange
        UserLoginRequestDTO dto = new UserLoginRequestDTO("user", "pass");
        UserLoginResponseDTO response = UserLoginResponseDTO.builder().token("token").tokenType("Bearer").build();
        when(authService.login(any(UserLoginRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));

        verify(authService, times(1)).login(any(UserLoginRequestDTO.class));
    }
}
