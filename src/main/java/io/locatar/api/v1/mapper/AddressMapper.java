package io.locatar.api.v1.mapper;

import io.locatar.api.v1.dto.AddressDTO;
import io.locatar.domain.address.AddressEntity;

public class AddressMapper {

    public static AddressDTO toDTO(AddressEntity entity) {
        if (entity == null) return null;

        return AddressDTO.builder()
                .street(entity.getStreet())
                .number(entity.getNumber())
                .complement(entity.getComplement())
                .neighborhood(entity.getNeighborhood())
                .city(entity.getCity())
                .state(entity.getState())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .build();
    }

    public static AddressEntity toEntity(AddressDTO dto) {
        if (dto == null) return null;

        return AddressEntity.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .neighborhood(dto.getNeighborhood())
                .city(dto.getCity())
                .state(dto.getState())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}
