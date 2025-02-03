package org.example.orderservice.repository;

import org.example.orderservice.entity.CartItemEntity;
import org.example.orderservice.entity.CartEntity;
import org.example.orderservice.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    // Added to ensure that the cart item being updated belongs to the correct cart.
    Optional<CartItemEntity> findByIdAndCartId(Long itemId, Long cartId);
    // New method to find an item in a cart by its product
    Optional<CartItemEntity> findByCartAndProduct(CartEntity cart, ProductEntity product);
} 