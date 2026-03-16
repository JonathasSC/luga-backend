package io.locatar.domain.address;

import io.locatar.domain.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
