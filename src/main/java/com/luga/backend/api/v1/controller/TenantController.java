package com.luga.backend.api.v1.controller;

import com.luga.backend.api.v1.dto.TenantDTO;
import com.luga.backend.api.v1.service.TenantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {
    
    private final TenantService service;

    @GetMapping
    public List<TenantDTO> list() {
        return service.listAll();
    }

    @PostMapping
    public TenantDTO create(@RequestBody @Valid TenantDTO dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
