package com.luga.backend.api.v1.mapper;

import com.luga.backend.api.v1.dto.PropertyDTO;
import com.luga.backend.domain.entity.PropertyEntity;

public class PropertyMapper {

    public static PropertyDTO toDTO(PropertyEntity entity) {
        if (entity == null) return null;

        return PropertyDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .address(AddressMapper.toDTO(entity.getAddress()))
                .build();
    }

    public static PropertyEntity toEntity(PropertyDTO dto) {
        if (dto == null) return null;

        return PropertyEntity.builder()
                .name(dto.getName())
                .status(dto.getStatus())
                .address(AddressMapper.toEntity(dto.getAddress()))
                .build();
    }
}
