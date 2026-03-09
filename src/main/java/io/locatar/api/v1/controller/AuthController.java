package io.locatar.api.v1.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.locatar.api.v1.dto.*;
import io.locatar.api.v1.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDTO> register(
            @RequestBody @Valid UserRegisterRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(dto));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(
            @RequestBody @Valid UserLoginRequestDTO dto) {

        return ResponseEntity.ok(authService.login(dto));
    }
}