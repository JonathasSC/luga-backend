package com.luga.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import com.luga.backend.api.v1.enums.PropertyStatus;

@Entity
@Table(name = "properties")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "property_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class PropertyBaseEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = true)
    private TenantEntity tenant;
}