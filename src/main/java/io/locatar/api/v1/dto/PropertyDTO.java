package io.locatar.api.v1.dto;

import io.locatar.api.v1.enums.PropertyStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class PropertyDTO {

    private UUID id;

    private String name;

    private PropertyStatus status;

    private AddressDTO address;
}