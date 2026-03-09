package com.luga.backend.domain.repository;

import com.luga.backend.domain.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApartmentRepository extends JpaRepository<ApartmentEntity, UUID> {
}