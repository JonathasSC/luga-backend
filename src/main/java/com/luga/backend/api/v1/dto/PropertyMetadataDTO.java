package com.luga.backend.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PropertyMetadataDTO {

    private long totalProperties;
    private long availableProperties;
    private long rentedProperties;
    
    private BigDecimal totalRentValue;
}
