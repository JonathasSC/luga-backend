package com.luga.backend.api.v1.controller;

import com.luga.backend.api.v1.dto.ApartmentDTO;
import com.luga.backend.api.v1.service.ApartmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @GetMapping
    public List<ApartmentDTO> listAll() {
        return apartmentService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDTO create(@RequestBody ApartmentDTO dto) {
        return apartmentService.create(dto);
    }

    @PutMapping("/{id}")
    public ApartmentDTO update(@PathVariable UUID id,
                               @RequestBody ApartmentDTO dto) {
        return apartmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        apartmentService.delete(id);
    }
}