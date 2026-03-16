package io.locatar.domain.base;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    private java.time.Instant createdAt;
    private java.time.Instant updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = java.time.Instant.now();
        updatedAt = java.time.Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = java.time.Instant.now();
    }
}
