package io.locatar.domain.house;

import io.locatar.api.v1.dto.AddressDTO;
import io.locatar.api.v1.dto.HouseDTO;
import io.locatar.api.v1.enums.PropertyStatus;
import io.locatar.api.v1.service.CurrentUserService;
import io.locatar.domain.address.AddressEntity;
import io.locatar.domain.user.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseServiceTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private CurrentUserService currentUserService;

    @InjectMocks
    private HouseService houseService;

    @Test
    @DisplayName("Should list my houses")
    void listMyHouses_ShouldReturnHouseDTOList() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        when(currentUserService.getCurrentUser()).thenReturn(user);

        HouseEntity house = HouseEntity.builder()
                .name("Test House")
                .status(PropertyStatus.AVAILABLE)
                .address(new AddressEntity())
                .build();
        when(houseRepository.findByUser(user)).thenReturn(List.of(house));

        // Act
        List<HouseDTO> result = houseService.listMyHouses();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test House");
        verify(currentUserService).getCurrentUser();
        verify(houseRepository).findByUser(user);
    }

    @Test
    @DisplayName("Should create a house for current user")
    void create_ShouldSaveAndReturnDTO() {
        // Arrange
        UserEntity user = new UserEntity();
        when(currentUserService.getCurrentUser()).thenReturn(user);

        AddressDTO addressDTO = AddressDTO.builder().street("Main St").build();
        HouseDTO dto = HouseDTO.builder()
                .name("New House")
                .address(addressDTO)
                .build();

        HouseEntity savedEntity = new HouseEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setName("New House");
        savedEntity.setAddress(new AddressEntity());

        when(houseRepository.save(any(HouseEntity.class))).thenReturn(savedEntity);

        // Act
        HouseDTO result = houseService.create(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("New House");
        verify(houseRepository).save(any(HouseEntity.class));
    }

    @Test
    @DisplayName("Should update house details")
    void update_ShouldUpdateAndReturnDTO() {
        // Arrange
        UUID id = UUID.randomUUID();
        AddressEntity addressEntity = new AddressEntity();
        HouseEntity existingHouse = HouseEntity.builder()
                .name("Old Name")
                .address(addressEntity)
                .build();
        existingHouse.setId(id);

        AddressDTO addressDTO = AddressDTO.builder()
                .street("New St")
                .city("New City")
                .build();
        HouseDTO updateDTO = HouseDTO.builder()
                .name("New Name")
                .address(addressDTO)
                .status(PropertyStatus.AVAILABLE)
                .hasGarage(true)
                .build();

        when(houseRepository.findById(id)).thenReturn(Optional.of(existingHouse));

        // Act
        HouseDTO result = houseService.update(id, updateDTO);

        // Assert
        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(existingHouse.getName()).isEqualTo("New Name");
        assertThat(existingHouse.getAddress().getStreet()).isEqualTo("New St");
        assertThat(existingHouse.getHasGarage()).isTrue();
    }

    @Test
    @DisplayName("Should throw exception when house not found during update")
    void update_ShouldThrowException_WhenNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        HouseDTO updateDTO = HouseDTO.builder().build();
        when(houseRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> houseService.update(id, updateDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("House not found");
    }

    @Test
    @DisplayName("Should delete house")
    void delete_ShouldCallRepository() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        houseService.delete(id);

        // Assert
        verify(houseRepository).deleteById(id);
    }
}
