package org.example.orderservice.repository;

import org.example.orderservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserId(Long userId);
    
    // Add methods for cart operations
    Optional<OrderEntity> findByUserIdAndStatus(Long userId, String status);
    List<OrderEntity> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, String status);
    boolean existsByUserIdAndStatus(Long userId, String status);
} 