package com.luga.backend.api.v1.mapper;

import com.luga.backend.api.v1.dto.ApartmentDTO;
import com.luga.backend.domain.entity.ApartmentEntity;

public class ApartmentMapper {

    private ApartmentMapper() {}

    public static ApartmentDTO toDTO(ApartmentEntity entity) {
        if (entity == null) {
            return null;
        }

        return ApartmentDTO.builder()
                // ===== Campos herdados =====
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .address(AddressMapper.toDTO(entity.getAddress()))

                // ===== Campos específicos =====
                .floor(entity.getFloor())
                .unitNumber(entity.getUnitNumber())
                .condoFee(entity.getCondoFee())

                .build();
    }

    public static ApartmentEntity toEntity(ApartmentDTO dto) {
        if (dto == null) {
            return null;
        }

        ApartmentEntity entity = new ApartmentEntity();

        // ===== Campos herdados =====
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setAddress(AddressMapper.toEntity(dto.getAddress()));

        // ===== Campos específicos =====
        entity.setFloor(dto.getFloor());
        entity.setUnitNumber(dto.getUnitNumber());
        entity.setCondoFee(dto.getCondoFee());

        return entity;
    }
}