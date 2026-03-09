package io.locatar.api.v1.controller;

import io.locatar.api.v1.dto.HouseDTO;
import io.locatar.domain.house.HouseService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<HouseDTO> listAll() {
        return houseService.listAll();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HouseDTO create(@RequestBody HouseDTO dto) {
        return houseService.create(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public HouseDTO update(@PathVariable UUID id,
                           @RequestBody HouseDTO dto) {
        return houseService.update(id, dto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        houseService.delete(id);
    }
}