package com.luga.backend.api.v1.service;

import com.luga.backend.api.v1.dto.TenantDTO;
import com.luga.backend.api.v1.mapper.TenantMapper;
import com.luga.backend.domain.repository.TenantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository repository;

    public List<TenantDTO> listAll() {
        return repository.findAll()
                .stream()
                .map(TenantMapper::toDTO)
                .toList();
    }

    public TenantDTO create(TenantDTO dto) {
        var entity = TenantMapper.toEntity(dto);
        return TenantMapper.toDTO(repository.save(entity));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
