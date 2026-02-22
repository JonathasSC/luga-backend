package com.luga.backend.api.v1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private UUID id;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Number is required")
    private String number;

    private String complement;

    @NotBlank(message = "Neighborhood is required")
    private String neighborhood;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    private Double latitude;
    private Double longitude;
}
