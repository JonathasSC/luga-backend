package io.locatar.domain.tenant;

import io.locatar.api.v1.dto.TenantDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {

    @Mock
    private TenantRepository repository;

    @InjectMocks
    private TenantService service;

    @Test
    @DisplayName("Should list all tenants and return as DTO list")
    void listAll_ShouldReturnTenantDTOList() {
        // Arrange
        var tenant1 = TenantEntity.builder().name("John Doe").cpf("12345678901").phoneNumber("999999999").build();
        var tenant2 = TenantEntity.builder().name("Jane Doe").cpf("10987654321").phoneNumber("888888888").build();
        when(repository.findAll()).thenReturn(List.of(tenant1, tenant2));

        // Act
        List<TenantDTO> result = service.listAll();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        assertThat(result.get(1).getName()).isEqualTo("Jane Doe");
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should create a new tenant and return as DTO")
    void create_ShouldSaveAndReturnDTO() {

        var dto = TenantDTO.builder()
                .name("John Doe")
                .cpf("12345678901")
                .phoneNumber("999999999")
                .build();

        var savedEntity = TenantEntity.builder()
                .name("John Doe")
                .cpf("12345678901")
                .phoneNumber("999999999")
                .build();
        savedEntity.setId(UUID.randomUUID());

        when(repository.save(any(TenantEntity.class))).thenReturn(savedEntity);

        TenantDTO result = service.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(savedEntity.getId());
        assertThat(result.getName()).isEqualTo("John Doe");

        verify(repository).save(any(TenantEntity.class));
    }

    @Test
    @DisplayName("Should delete tenant by ID")
    void delete_ShouldCallRepositoryDeleteById() {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(repository).deleteById(id);

        // Act
        service.delete(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}
