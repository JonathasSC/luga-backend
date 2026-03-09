package io.locatar.api.v1.controller;

import io.locatar.api.v1.dto.ApartmentDTO;
import io.locatar.domain.apartment.ApartmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<ApartmentDTO> listAll() {
        return apartmentService.listAll();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDTO create(@RequestBody ApartmentDTO dto) {
        return apartmentService.create(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ApartmentDTO update(@PathVariable UUID id,
                               @RequestBody ApartmentDTO dto) {
        return apartmentService.update(id, dto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        apartmentService.delete(id);
    }
}