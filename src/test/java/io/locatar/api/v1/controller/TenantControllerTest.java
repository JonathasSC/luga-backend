package io.locatar.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.locatar.api.v1.dto.TenantDTO;
import io.locatar.api.v1.service.JwtService;
import io.locatar.domain.tenant.TenantService;
import io.locatar.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TenantController.class)
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TenantService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should list all tenants with authentication")
    @WithMockUser
    void list_ShouldReturnTenantList() throws Exception {
        // Arrange
        var tenant = TenantDTO.builder().name("John Doe").cpf("12345678901").build();
        when(service.listAll()).thenReturn(List.of(tenant));

        // Act & Assert
        mockMvc.perform(get("/api/v1/tenants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(service, times(1)).listAll();
    }

    @Test
    @DisplayName("Should fail when accessing tenants list without authentication")
    void list_ShouldFailWithoutAuth() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/tenants"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Should create a new tenant with authentication")
    @WithMockUser
    void create_ShouldReturnSavedTenant() throws Exception {
        // Arrange
        var dto = TenantDTO.builder().name("Jane Doe").cpf("10987654321").phoneNumber("999999999").build();
        when(service.create(any(TenantDTO.class))).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/tenants")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        verify(service, times(1)).create(any(TenantDTO.class));
    }

    @Test
    @DisplayName("Should delete tenant by ID with authentication")
    @WithMockUser
    void delete_ShouldCallService() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(service).delete(id);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/tenants/{id}", id)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(id);
    }
}
