package io.locatar.domain.property;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import io.locatar.api.v1.enums.PropertyStatus;
import io.locatar.domain.address.AddressEntity;
import io.locatar.domain.base.BaseEntity;
import io.locatar.domain.user.UserEntity;

@Entity
@Table(name = "properties")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "property_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class PropertyEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}