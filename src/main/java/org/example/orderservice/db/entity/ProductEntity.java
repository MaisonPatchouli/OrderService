package org.example.orderservice.db.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public record ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,

    @Column(nullable = false)
    String name,

    String description,

    @Column(nullable = false)
    BigDecimal price,

    @Column(nullable = false)
    Integer stock,

    @Column(name = "created_at")
    LocalDateTime createdAt
) {
    public ProductEntity {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
} 