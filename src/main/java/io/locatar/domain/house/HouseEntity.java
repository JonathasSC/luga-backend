package io.locatar.domain.house;

import io.locatar.domain.property.PropertyEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "houses")
@DiscriminatorValue("HOUSE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HouseEntity extends PropertyEntity {

    private Boolean hasGarage;

    private Boolean hasYard;

    private Double lotSize;

    private Integer numberOfFloors;
    
}