package io.locatar.domain.apartment;

import io.locatar.api.v1.dto.AddressDTO;
import io.locatar.api.v1.dto.ApartmentDTO;
import io.locatar.api.v1.enums.PropertyStatus;
import io.locatar.domain.address.AddressEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @InjectMocks
    private ApartmentService apartmentService;

    @Test
    @DisplayName("Should list all apartments")
    void listAll_ShouldReturnApartmentDTOList() {
        // Arrange
        ApartmentEntity apartment = ApartmentEntity.builder()
                .name("Luxury Apt")
                .status(PropertyStatus.AVAILABLE)
                .address(new AddressEntity())
                .build();
        when(apartmentRepository.findAll()).thenReturn(List.of(apartment));

        // Act
        List<ApartmentDTO> result = apartmentService.listAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Luxury Apt");
        verify(apartmentRepository).findAll();
    }

    @Test
    @DisplayName("Should create an apartment")
    void create_ShouldSaveAndReturnDTO() {
        // Arrange
        AddressDTO addressDTO = AddressDTO.builder().street("Park Ave").build();
        ApartmentDTO dto = ApartmentDTO.builder()
                .name("Apt 101")
                .address(addressDTO)
                .build();

        ApartmentEntity savedEntity = new ApartmentEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setName("Apt 101");
        savedEntity.setAddress(new AddressEntity());

        when(apartmentRepository.save(any(ApartmentEntity.class))).thenReturn(savedEntity);

        // Act
        ApartmentDTO result = apartmentService.create(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Apt 101");
        verify(apartmentRepository).save(any(ApartmentEntity.class));
    }

    @Test
    @DisplayName("Should update apartment details")
    void update_ShouldUpdateAndReturnDTO() {
        // Arrange
        UUID id = UUID.randomUUID();
        AddressEntity addressEntity = new AddressEntity();
        ApartmentEntity existingApt = ApartmentEntity.builder()
                .name("Old Name")
                .address(addressEntity)
                .build();
        existingApt.setId(id);

        AddressDTO addressDTO = AddressDTO.builder()
                .street("New St")
                .build();
        ApartmentDTO updateDTO = ApartmentDTO.builder()
                .name("New Name")
                .address(addressDTO)
                .status(PropertyStatus.RENTED)
                .floor(5)
                .unitNumber("505")
                .condoFee(new BigDecimal("500.00"))
                .build();

        when(apartmentRepository.findById(id)).thenReturn(Optional.of(existingApt));

        // Act
        ApartmentDTO result = apartmentService.update(id, updateDTO);

        // Assert
        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(existingApt.getName()).isEqualTo("New Name");
        assertThat(existingApt.getFloor()).isEqualTo(5);
        assertThat(existingApt.getUnitNumber()).isEqualTo("505");
        assertThat(existingApt.getCondoFee()).isEqualTo(new BigDecimal("500.00"));
    }

    @Test
    @DisplayName("Should throw exception when apartment not found during update")
    void update_ShouldThrowException_WhenNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        ApartmentDTO updateDTO = ApartmentDTO.builder().build();
        when(apartmentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> apartmentService.update(id, updateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Apartment not found");
    }

    @Test
    @DisplayName("Should delete apartment")
    void delete_ShouldCallRepository() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        apartmentService.delete(id);

        // Assert
        verify(apartmentRepository).deleteById(id);
    }
}
