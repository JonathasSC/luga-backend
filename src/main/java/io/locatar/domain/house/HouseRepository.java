package io.locatar.domain.house;

import org.springframework.data.jpa.repository.JpaRepository;

import io.locatar.domain.user.UserEntity;

import java.util.List;
import java.util.UUID;

public interface HouseRepository extends JpaRepository<HouseEntity, UUID> {
    List<HouseEntity> findByUser(UserEntity user);
}