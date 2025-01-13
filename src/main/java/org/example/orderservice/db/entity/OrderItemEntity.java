package org.example.orderservice.db.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
public record OrderItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    OrderEntity order,

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    ProductEntity product,

    @Column(nullable = false)
    Integer quantity,

    @Column(nullable = false)
    BigDecimal price,

    @Column(name = "created_at")
    LocalDateTime createdAt
) {
    public OrderItemEntity {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
} 