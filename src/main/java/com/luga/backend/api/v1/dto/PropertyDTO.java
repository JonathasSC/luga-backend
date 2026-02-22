package com.luga.backend.api.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

import com.luga.backend.api.v1.enums.PropertyStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDTO {

    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Status is required")
    private PropertyStatus status;

    @NotNull(message = "Address is required")
    private AddressDTO address;
}
