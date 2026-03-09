package com.luga.backend.api.v1.dto;

import jakarta.validation.constraints.NotBlank;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Password Confirmation is required")
    private String passwordConfirm;
}
