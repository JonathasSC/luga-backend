package com.luga.backend.api.v1.service;

import com.luga.backend.api.v1.dto.HouseDTO;
import com.luga.backend.api.v1.mapper.HouseMapper;
import com.luga.backend.domain.entity.HouseEntity;
import com.luga.backend.domain.repository.HouseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;

    @Transactional(readOnly = true)
    public List<HouseDTO> listAll() {
        return houseRepository.findAll()
                .stream()
                .map(HouseMapper::toDTO)
                .toList();
    }

    @Transactional
    public HouseDTO create(HouseDTO dto) {
        HouseEntity entity = HouseMapper.toEntity(dto);
        HouseEntity saved = houseRepository.save(entity);
        return HouseMapper.toDTO(saved);
    }

    @Transactional
    public HouseDTO update(UUID id, HouseDTO dto) {
        HouseEntity entity = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));

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
        entity.setHasGarage(dto.getHasGarage());
        entity.setHasYard(dto.getHasYard());
        entity.setLotSize(dto.getLotSize());
        entity.setNumberOfFloors(dto.getNumberOfFloors());

        return HouseMapper.toDTO(entity);
    }

    @Transactional
    public void delete(UUID id) {
        houseRepository.deleteById(id);
    }
}