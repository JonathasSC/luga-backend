package com.luga.backend.api.v1.mapper;

import com.luga.backend.api.v1.dto.TenantDTO;
import com.luga.backend.domain.entity.TenantEntity;

public class TenantMapper {

    public static TenantDTO toDTO(TenantEntity entity) {
        if (entity == null) return null;

        return TenantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }

    public static TenantEntity toEntity(TenantDTO dto) {
        if (dto == null) return null;

        return TenantEntity.builder()
                .name(dto.getName())
                .cpf(dto.getCpf())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
