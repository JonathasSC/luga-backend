package com.luga.backend.api.v1.dto;

import java.math.BigDecimal;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ApartmentDTO extends PropertyDTO {

    private Integer floor;
    private String unitNumber;
    private Boolean hasElevator;
    private BigDecimal condoFee;
}