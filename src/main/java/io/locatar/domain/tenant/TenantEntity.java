package io.locatar.domain.tenant;

import io.locatar.domain.base.BaseEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "tenants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    private String name;

    private String cpf;

    private String phoneNumber;

}
