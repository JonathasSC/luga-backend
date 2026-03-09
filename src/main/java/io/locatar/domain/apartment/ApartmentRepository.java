package io.locatar.domain.apartment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApartmentRepository extends JpaRepository<ApartmentEntity, UUID> {
}