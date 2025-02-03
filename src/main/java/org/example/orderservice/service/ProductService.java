package org.example.orderservice.service;

import org.example.orderservice.repository.ProductRepository;
import org.example.orderservice.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }
} 