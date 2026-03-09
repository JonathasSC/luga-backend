package io.locatar.domain.apartment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import io.locatar.domain.property.PropertyEntity;

@Entity
@Table(name="apartements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApartmentEntity extends PropertyEntity{
    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String unitNumber;

    private BigDecimal condoFee;

}
