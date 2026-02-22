package com.luga.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AddressEntity extends BaseEntity {

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String number;
    
    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false)
    private String country;

    private Double latitude;
    private Double longitude;
}
