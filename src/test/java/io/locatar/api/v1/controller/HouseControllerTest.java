package io.locatar.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.locatar.api.v1.dto.HouseDTO;
import io.locatar.api.v1.service.JwtService;
import io.locatar.domain.house.HouseService;
import io.locatar.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(HouseController.class)
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HouseService houseService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should list all houses when authenticated")
    @WithMockUser
    void listAll_ShouldReturnHouseList() throws Exception {
        // Arrange
        HouseDTO house = HouseDTO.builder().name("Beach House").build();
        when(houseService.listAll()).thenReturn(List.of(house));

        // Act & Assert
        mockMvc.perform(get("/api/v1/houses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Beach House"));

        verify(houseService, times(1)).listAll();
    }

    @Test
    @DisplayName("Should create house when authenticated")
    @WithMockUser
    void create_ShouldReturnCreatedHouse() throws Exception {
        // Arrange
        HouseDTO dto = HouseDTO.builder().name("New House").build();
        when(houseService.create(any(HouseDTO.class))).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(post("/api/v1/houses")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New House"));

        verify(houseService, times(1)).create(any(HouseDTO.class));
    }

    @Test
    @DisplayName("Should update house when authenticated")
    @WithMockUser
    void update_ShouldReturnUpdatedHouse() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        HouseDTO dto = HouseDTO.builder().name("Updated House").build();
        when(houseService.update(eq(id), any(HouseDTO.class))).thenReturn(dto);

        // Act & Assert
        mockMvc.perform(put("/api/v1/houses/{id}", id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated House"));

        verify(houseService, times(1)).update(eq(id), any(HouseDTO.class));
    }

    @Test
    @DisplayName("Should delete house and return NO_CONTENT")
    @WithMockUser
    void delete_ShouldReturnNoContent() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(houseService).delete(id);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/houses/{id}", id)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(houseService, times(1)).delete(id);
    }
}
