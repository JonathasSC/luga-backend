package io.locatar.domain.apartment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.locatar.api.v1.dto.ApartmentDTO;
import io.locatar.api.v1.mapper.ApartmentMapper;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Transactional(readOnly = true)
    public List<ApartmentDTO> listAll() {
        return apartmentRepository.findAll()
                .stream()
                .map(ApartmentMapper::toDTO)
                .toList();
    }

    @Transactional
    public ApartmentDTO create(ApartmentDTO dto) {
        ApartmentEntity entity = ApartmentMapper.toEntity(dto);
        ApartmentEntity saved = apartmentRepository.save(entity);
        return ApartmentMapper.toDTO(saved);
    }

    @Transactional
    public ApartmentDTO update(UUID id, ApartmentDTO dto) {
        ApartmentEntity entity = apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));

        // ===== Campos base =====
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

        // ===== Campos específicos =====
        entity.setFloor(dto.getFloor());
        entity.setUnitNumber(dto.getUnitNumber());
        entity.setCondoFee(dto.getCondoFee());

        return ApartmentMapper.toDTO(entity);
    }

    @Transactional
    public void delete(UUID id) {
        apartmentRepository.deleteById(id);
    }
}