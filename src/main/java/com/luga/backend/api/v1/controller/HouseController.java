package com.luga.backend.api.v1.controller;

import com.luga.backend.api.v1.dto.HouseDTO;
import com.luga.backend.api.v1.service.HouseService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping
    public List<HouseDTO> listAll() {
        return houseService.listAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HouseDTO create(@RequestBody HouseDTO dto) {
        return houseService.create(dto);
    }

    @PutMapping("/{id}")
    public HouseDTO update(@PathVariable UUID id,
                           @RequestBody HouseDTO dto) {
        return houseService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        houseService.delete(id);
    }
}