package com.luga.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tenants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    private String name;

    private String cpf;

    private String phoneNumber;

}
