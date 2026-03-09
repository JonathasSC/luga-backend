package io.locatar.api.v1.mapper;

import io.locatar.api.v1.dto.HouseDTO;
import io.locatar.domain.house.HouseEntity;

public class HouseMapper {

    private HouseMapper() {}

    public static HouseDTO toDTO(HouseEntity entity) {
        if (entity == null) {
            return null;
        }

        return HouseDTO.builder()
                // Campos herdados de PropertyBaseEntity
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .address(AddressMapper.toDTO(entity.getAddress()))

                // Campos específicos de House
                .hasGarage(entity.getHasGarage())
                .hasYard(entity.getHasYard())
                .lotSize(entity.getLotSize())
                .numberOfFloors(entity.getNumberOfFloors())
                .build();
    }

    public static HouseEntity toEntity(HouseDTO dto) {
        if (dto == null) {
            return null;
        }

        HouseEntity entity = new HouseEntity();

        // Campos herdados
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setAddress(AddressMapper.toEntity(dto.getAddress()));

        // Campos específicos
        entity.setHasGarage(dto.getHasGarage());
        entity.setHasYard(dto.getHasYard());
        entity.setLotSize(dto.getLotSize());
        entity.setNumberOfFloors(dto.getNumberOfFloors());

        return entity;
    }
}