package com.luga.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import com.luga.backend.api.v1.enums.PropertyStatus;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PropertyEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;
   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;
}
