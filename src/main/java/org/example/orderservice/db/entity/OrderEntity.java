package org.example.orderservice.db.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public record OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user,

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItemEntity> items,

    @Column(nullable = false)
    BigDecimal totalPrice,

    @Column(name = "created_at")
    LocalDateTime createdAt
) {
    public OrderEntity {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
} 