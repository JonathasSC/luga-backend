package com.luga.backend.api.v1.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class HouseDTO extends PropertyDTO {

    private Boolean hasGarage;
    private Boolean hasYard;
    private Double lotSize;
    private Integer numberOfFloors;
}