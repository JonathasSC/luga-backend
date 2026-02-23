package com.luga.backend.api.v1.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luga.backend.api.v1.dto.UserLoginResponseDTO;
import com.luga.backend.api.v1.dto.MessageResponseDTO;
import com.luga.backend.api.v1.dto.UserLoginRequestDTO;
import com.luga.backend.api.v1.dto.UserRegisterRequestDTO;
import com.luga.backend.domain.entity.UserEntity;
import com.luga.backend.domain.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public MessageResponseDTO register(UserRegisterRequestDTO dto) {

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);

        return MessageResponseDTO.builder()
                .message("User registered successfully")
                .build();
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO dto) {
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> 
                    new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        }

        String token = jwtService.generateToken(user);

        return UserLoginResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}