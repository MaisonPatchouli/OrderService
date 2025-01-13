package org.example.orderservice.order;

import org.example.orderservice.db.OrderRepository;
import org.example.orderservice.db.CartRepository;
import org.example.orderservice.db.entity.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    public OrderEntity createOrder(Long cartId) {
        CartEntity cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<OrderItemEntity> orderItems = cart.items().stream()
            .map(cartItem -> new OrderItemEntity(
                null,
                null, // This will be set after order is created
                cartItem.product(),
                cartItem.quantity(),
                cartItem.product().price(),
                null
            ))
            .collect(Collectors.toList());

        BigDecimal totalPrice = orderItems.stream()
            .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = new OrderEntity(
            null,
            cart.user(),
            orderItems,
            totalPrice,
            null
        );

        return orderRepository.save(order);
    }

    public OrderEntity getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderEntity> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
} 