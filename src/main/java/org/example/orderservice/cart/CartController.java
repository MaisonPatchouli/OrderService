package org.example.orderservice.cart;

import org.example.orderservice.db.entity.CartItemEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/items")
    public CartItemEntity addToCart(
        @PathVariable Long cartId,
        @RequestParam Long productId,
        @RequestParam Integer quantity
    ) {
        return cartService.addToCart(cartId, productId, quantity);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public void removeFromCart(
        @PathVariable Long cartId,
        @PathVariable Long itemId
    ) {
        cartService.removeFromCart(cartId, itemId);
    }
} 