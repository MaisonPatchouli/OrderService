package org.example.orderservice.controller;

import org.example.orderservice.entity.CartEntity;
import org.example.orderservice.entity.CartItemEntity;
import org.example.orderservice.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    
    @Autowired
    private CartService cartService;

    @PostMapping("/user/{userId}/items")
    public ResponseEntity<?> addItemToCartByUserId(
            @PathVariable Long userId,
            @RequestBody CartItemEntity cartItem) {
        try {
            logger.info("Adding item to cart for user {}", userId);
            if (cartItem == null) {
                logger.error("Cart item is null");
                return ResponseEntity
                    .badRequest()
                    .body("Cart item cannot be null");
            }
            if (cartItem.getProduct() == null) {
                logger.error("Product is null in cart item");
                return ResponseEntity
                    .badRequest()
                    .body("Product cannot be null");
            }
            if (cartItem.getProduct().getId() == null) {
                logger.error("Product ID is null");
                return ResponseEntity
                    .badRequest()
                    .body("Product ID cannot be null");
            }
            
            CartEntity cart = cartService.getUserCart(userId);
            logger.info("Found cart {} for user {}", cart.getId(), userId);
            
            CartItemEntity newItem = cartService.addToCart(cart.getId(), cartItem);
            logger.info("Successfully added item to cart: {}", newItem);
            return ResponseEntity.ok(newItem);
        } catch (RuntimeException e) {
            logger.error("Error adding item to cart: {}", e.getMessage(), e);
            return ResponseEntity
                .internalServerError()
                .body("Error adding item to cart: " + e.getMessage());
        }
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemEntity> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemEntity cartItem) {
        CartItemEntity newItem = cartService.addToCart(cartId, cartItem);
        return ResponseEntity.ok(newItem);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeFromCart(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<CartEntity> createCart(@PathVariable Long userId) {
        try {
            CartEntity cart = cartService.createCartForUser(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartEntity> getUserCart(@PathVariable Long userId) {
        try {
            CartEntity cart = cartService.getUserCart(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cartId}/items/{itemId}/increase")
    public ResponseEntity<CartItemEntity> increaseQuantity(
        @PathVariable Long cartId,
        @PathVariable Long itemId
    ) {
        CartItemEntity updatedItem = cartService.increaseItemQuantity(cartId, itemId);
        return ResponseEntity.ok(updatedItem);
    }

    @PostMapping("/{cartId}/items/{itemId}/decrease")
    public ResponseEntity<?> decreaseQuantity(
        @PathVariable Long cartId,
        @PathVariable Long itemId
    ) {
        CartItemEntity updatedItem = cartService.decreaseItemQuantity(cartId, itemId);
        if (updatedItem == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(updatedItem);
    }
} 