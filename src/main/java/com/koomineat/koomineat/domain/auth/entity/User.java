package com.koomineat.koomineat.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
// postgres와 충돌 피하기 위해.
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // UUID.
    @Column(unique = true)
    private String authToken;
}
