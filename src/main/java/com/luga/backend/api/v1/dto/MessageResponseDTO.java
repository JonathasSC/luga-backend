package com.luga.backend.api.v1.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class MessageResponseDTO {
    private String message;
}