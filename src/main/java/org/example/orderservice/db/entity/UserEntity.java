package org.example.orderservice.db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public record UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,

    @Column(nullable = false, unique = true)
    String username,

    @Column(nullable = false, unique = true)
    String email,

    @Column(nullable = false)
    String password,

    @Column(name = "created_at")
    LocalDateTime createdAt
) {
    public UserEntity {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
} 