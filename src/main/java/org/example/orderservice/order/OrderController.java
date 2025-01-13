package org.example.orderservice.order;

import org.example.orderservice.db.entity.OrderEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderEntity createOrder(@RequestParam Long cartId) {
        return orderService.createOrder(cartId);
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