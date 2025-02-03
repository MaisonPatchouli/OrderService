package org.example.orderservice.service;

import org.example.orderservice.repository.CartRepository;
import org.example.orderservice.repository.CartItemRepository;
import org.example.orderservice.entity.CartEntity;
import org.example.orderservice.entity.CartItemEntity;
import org.example.orderservice.entity.ProductEntity;
import org.example.orderservice.entity.UserEntity;
import org.example.orderservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final UserRepository userRepository;

    public CartService(
        CartRepository cartRepository,
        CartItemRepository cartItemRepository,
        ProductService productService,
        UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    public CartItemEntity addToCart(Long cartId, CartItemEntity cartItem) {
        CartEntity cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Extract product details from the passed cartItem.
        Long productId = cartItem.getProduct().getId();
        ProductEntity product = productService.getProductById(productId);
        
        // Check if the item already exists in the cart.
        Optional<CartItemEntity> existingItemOptional = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItemOptional.isPresent()) {
            // Increment existing item's quantity by 1.
            CartItemEntity existingItem = existingItemOptional.get();
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            return cartItemRepository.save(existingItem);
        }

        // If item does not exist, set the associations and default quantity.
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        if (cartItem.getQuantity() == null || cartItem.getQuantity() <= 0) {
            cartItem.setQuantity(1);
        }
        return cartItemRepository.save(cartItem);
    }

    public void removeFromCart(Long cartId, Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public CartItemEntity increaseItemQuantity(Long cartId, Long itemId) {
        Optional<CartItemEntity> optionalCartItem = cartItemRepository.findByIdAndCartId(itemId, cartId);
        if (!optionalCartItem.isPresent()) {
            throw new RuntimeException("Cart item not found");
        }
        CartItemEntity cartItem = optionalCartItem.get();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        return cartItemRepository.save(cartItem);
    }

    public CartItemEntity decreaseItemQuantity(Long cartId, Long itemId) {
        Optional<CartItemEntity> optionalCartItem = cartItemRepository.findByIdAndCartId(itemId, cartId);
        if (!optionalCartItem.isPresent()) {
            throw new RuntimeException("Cart item not found");
        }
        CartItemEntity cartItem = optionalCartItem.get();
        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            return cartItemRepository.save(cartItem);
        } else {
            cartItemRepository.delete(cartItem);
            return null;
        }
    }

    public CartEntity createCartForUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        CartEntity cart = CartEntity.builder()
            .user(user)
            .items(new ArrayList<>())
            .build();
        return cartRepository.save(cart);
    }

    public CartEntity getUserCart(Long userId) {
        return cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found for user"));
    }
} 