package com.luga.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {
    private String username;
    private String email;
    private String password;
}
