package com.luga.backend.api.v1.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDTO {
    private String token;
    private String tokenType;
}