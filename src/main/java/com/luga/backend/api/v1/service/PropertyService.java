package com.luga.backend.api.v1.service;

import com.luga.backend.api.v1.dto.PropertyDTO;
import com.luga.backend.api.v1.dto.PropertyMetadataDTO;
import com.luga.backend.api.v1.enums.PropertyStatus;
import com.luga.backend.api.v1.mapper.PropertyMapper;
import com.luga.backend.domain.entity.PropertyEntity;
import com.luga.backend.domain.repository.PropertyRepository;
import com.luga.backend.domain.repository.RentalContractRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final RentalContractRepository contractRepository;
    
    @Transactional(readOnly = true)
    public List<PropertyDTO> listAll() {
        return propertyRepository.findAllWithAddress()
                .stream()
                .map(PropertyMapper::toDTO)
                .toList();
    }

    @Transactional
    public PropertyDTO create(PropertyDTO dto) {
        PropertyEntity entity = PropertyMapper.toEntity(dto);
        PropertyEntity saved = propertyRepository.save(entity);
        return PropertyMapper.toDTO(saved);
    }

    @Transactional
    public PropertyDTO update(UUID id, PropertyDTO dto) {
        PropertyEntity entity = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());

        entity.getAddress().setStreet(dto.getAddress().getStreet());
        entity.getAddress().setNumber(dto.getAddress().getNumber());
        entity.getAddress().setComplement(dto.getAddress().getComplement());
        entity.getAddress().setNeighborhood(dto.getAddress().getNeighborhood());
        entity.getAddress().setCity(dto.getAddress().getCity());
        entity.getAddress().setState(dto.getAddress().getState());
        entity.getAddress().setPostalCode(dto.getAddress().getPostalCode());
        entity.getAddress().setCountry(dto.getAddress().getCountry());
        entity.getAddress().setLatitude(dto.getAddress().getLatitude());
        entity.getAddress().setLongitude(dto.getAddress().getLongitude());

        return PropertyMapper.toDTO(entity);
    }

    @Transactional
    public void delete(UUID id) {
        propertyRepository.deleteById(id);
    }

    public PropertyMetadataDTO fetchMetadata() {

        long totalProperties = propertyRepository.count();
        long available = propertyRepository.countByStatus(PropertyStatus.AVAILABLE);
        long rented = propertyRepository.countByStatus(PropertyStatus.RENTED);

        BigDecimal totalRent = contractRepository.sumAllRentValues();

        return new PropertyMetadataDTO(
                totalProperties,
                available,
                rented,
                totalRent
        );
    }
}
