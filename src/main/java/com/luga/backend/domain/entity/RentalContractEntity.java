package com.luga.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;


@Entity
@Table(name = "rental_contracts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RentalContractEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenant;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal rentValue;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private Integer durationInMonths;

    @Column(nullable = false)
    private LocalDate expirationDate;

    public void calculateExpirationDate() {
        if (this.startDate != null && this.durationInMonths != null) {
            this.expirationDate = this.startDate.plusMonths(this.durationInMonths);
        }
    }

    public boolean isExpired(Clock clock) {
        return expirationDate != null &&
            expirationDate.isBefore(LocalDate.now(clock));
    }

    @PrePersist
    @PreUpdate
    private void beforeSave() {
        if (durationInMonths == null || durationInMonths <= 0) {
            throw new IllegalStateException("durationInMonths must be > 0");
        }
        if (startDate == null) {
            throw new IllegalStateException("startDate is required");
        }
        this.expirationDate = startDate.plusMonths(durationInMonths);
    }
}
