package com.luga.backend.api.v1.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalContractDTO {
    private UUID id;
    
    private UUID tenantId;

    @NotNull(message = "Property ID is required")
    private UUID propertyId;

    @NotNull(message = "Rent value is required")
    private Double rentValue;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

}
