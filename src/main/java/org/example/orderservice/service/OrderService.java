package org.example.orderservice.service;

import org.example.orderservice.entity.CartEntity;
import org.example.orderservice.entity.CartItemEntity;
import org.example.orderservice.entity.OrderEntity;
import org.example.orderservice.entity.OrderItemEntity;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartItemRepository cartItemRepository;

    public OrderEntity checkoutCart(Long userId) {
        // Retrieve user's cart (throws if not found)
        CartEntity cart = cartService.getUserCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItemEntity> orderItems = new ArrayList<>();
        
        // Create order items from each cart item and calculate the total.
        for (CartItemEntity cartItem : cart.getItems()) {
            BigDecimal itemTotal = BigDecimal.valueOf(cartItem.getQuantity())
                                       .multiply(cartItem.getProduct().getPrice());
            total = total.add(itemTotal);
            
            OrderItemEntity orderItem = OrderItemEntity.builder()
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getProduct().getPrice())
                    .build();
            orderItems.add(orderItem);
        }

        // Build the order
        OrderEntity order = OrderEntity.builder()
                .user(cart.getUser())
                .orderDate(new Date())
                .total(total.doubleValue())
                .items(new ArrayList<>())
                .build();

        // Set back-reference for each order item
        for (OrderItemEntity orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        order.setItems(orderItems);

        OrderEntity savedOrder = orderRepository.save(order);

        // Optionally, clear the cart after checkout (depends on your business logic)
        cart.getItems().clear();
        // You could also persist the cart if required:
        // cartRepository.save(cart);

        return savedOrder;
    }

    public OrderEntity getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderEntity> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
} 