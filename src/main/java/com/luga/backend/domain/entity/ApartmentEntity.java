package com.luga.backend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="apartements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApartmentEntity extends PropertyBaseEntity{
    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private String unitNumber;

    private BigDecimal condoFee;

}
