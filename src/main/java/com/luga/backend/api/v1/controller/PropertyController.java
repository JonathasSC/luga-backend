package com.luga.backend.api.v1.controller;

import com.luga.backend.api.v1.dto.PropertyDTO;
import com.luga.backend.api.v1.dto.PropertyMetadataDTO;
import com.luga.backend.api.v1.service.PropertyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;

    @GetMapping
    public List<PropertyDTO> list() {
        return service.listAll();
    }
    
    @PostMapping
    public PropertyDTO create(@RequestBody @Valid PropertyDTO dto) {
        return service.create(dto);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/metadata")
    public PropertyMetadataDTO metadata() {
        return service.fetchMetadata();
    }
}
