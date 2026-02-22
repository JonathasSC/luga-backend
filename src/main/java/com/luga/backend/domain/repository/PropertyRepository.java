package com.luga.backend.domain.repository;

import com.luga.backend.api.v1.enums.PropertyStatus;
import com.luga.backend.domain.entity.PropertyEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PropertyRepository extends JpaRepository<PropertyEntity, UUID> {
    long countByStatus(PropertyStatus status);
    
    @Query("""
        SELECT p
        FROM PropertyEntity p
        LEFT JOIN FETCH p.address
    """)
    List<PropertyEntity> findAllWithAddress();
}
