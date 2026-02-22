package com.luga.backend.domain.repository;

import com.luga.backend.domain.entity.TenantEntity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {
}
