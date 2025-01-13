package org.example.orderservice.cart;

import org.example.orderservice.db.CartRepository;
import org.example.orderservice.db.CartItemRepository;
import org.example.orderservice.db.entity.CartEntity;
import org.example.orderservice.db.entity.CartItemEntity;
import org.example.orderservice.db.entity.ProductEntity;
import org.example.orderservice.product.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartService(
        CartRepository cartRepository,
        CartItemRepository cartItemRepository,
        ProductService productService
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public CartItemEntity addToCart(Long cartId, Long productId, Integer quantity) {
        CartEntity cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        ProductEntity product = productService.getProductById(productId);
        
        return cartItemRepository.save(new CartItemEntity(
            null,
            cart,
            product,
            quantity,
            null
        ));
    }

    public void removeFromCart(Long cartId, Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
} 