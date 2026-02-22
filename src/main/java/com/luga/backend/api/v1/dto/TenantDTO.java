package com.luga.backend.api.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantDTO {

    private UUID id;

    @NotBlank(message = "Tenant name is required")
    private String name;

    @NotBlank(message = "CPF is required")
    @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
    private String cpf;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private List<PropertyDTO> properties;
}
