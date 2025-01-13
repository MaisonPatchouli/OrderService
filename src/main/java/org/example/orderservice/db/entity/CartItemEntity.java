package org.example.orderservice.db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
public record CartItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    CartEntity cart,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    ProductEntity product,

    @Column(nullable = false)
    Integer quantity,

    @Column(name = "created_at")
    LocalDateTime createdAt
) {
    public CartItemEntity {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
} 