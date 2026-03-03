package com.luga.backend.domain.repository;

import com.luga.backend.domain.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HouseRepository extends JpaRepository<HouseEntity, UUID> {
}