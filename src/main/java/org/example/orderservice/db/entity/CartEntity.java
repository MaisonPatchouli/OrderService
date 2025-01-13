package org.example.orderservice.db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public record CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user,

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    List<CartItemEntity> items,

    @Column(name = "created_at")
    LocalDateTime createdAt
) {
    public CartEntity {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        items = items == null ? new ArrayList<>() : new ArrayList<>(items);
    }
} 