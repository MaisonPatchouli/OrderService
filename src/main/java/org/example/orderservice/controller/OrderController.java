package org.example.orderservice.controller;

import org.example.orderservice.entity.OrderEntity;
import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderEntity> checkoutCart(@RequestParam Long userId) {
        OrderEntity order = orderService.checkoutCart(userId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}")
    public OrderEntity getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/user/{userId}")
    public List<OrderEntity> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }
} 