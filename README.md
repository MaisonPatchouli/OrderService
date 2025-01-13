# Order Service

A simple Spring Boot backend service for handling e-commerce operations.

## Overview

This service provides basic e-commerce functionality including:
- User management
- Product catalog
- Shopping cart
- Order processing

## Tech Stack

- Java 17
- Spring Boot
- MySQL
- Maven
- Docker

## Project Structure

```plaintext
src/main/java/org/example/orderservice/
├── OrderServiceApplication.java
├── cart/
│   ├── CartController.java
│   └── CartService.java
├── db/
│   ├── CartRepository.java
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   ├── UserRepository.java
│   └── entity/
│       ├── CartEntity.java
│       ├── CartItemEntity.java
│       ├── OrderEntity.java
│       ├── ProductEntity.java
│       └── UserEntity.java
├── order/
│   ├── OrderController.java
│   └── OrderService.java
├── product/
│   ├── ProductController.java
│   └── ProductService.java
└── user/
    ├── UserController.java
    └── UserService.java
```

## API Endpoints

### Users

- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `POST /users` - Create new user

### Products

- `GET /products` - Get all products
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create new product

### Cart

- `POST /cart/{cartId}/items` - Add item to cart
- `DELETE /cart/{cartId}/items/{itemId}` - Remove item from cart

### Orders

- `GET /orders` - Get all orders
- `GET /orders/{id}` - Get order by ID
- `GET /orders/user/{userId}` - Get user's orders
- `POST /orders?cartId={cartId}` - Create order from cart

## Setup

1. Clone the repository

   ```bash
   git clone https://github.com/yourusername/order-service.git
   ```

2. Start MySQL using Docker

   ```bash
   docker-compose up -d
   ```

3. Build and run the application

   ```bash
   ./mvnw spring-boot:run
   ```

## Testing with Postman

1. Create a user:

   ```http
   POST http://localhost:8080/users
   Content-Type: application/json

   {
       "username": "testuser",
       "email": "test@example.com",
       "password": "password123"
   }
   ```

2. Create a product:

   ```http
   POST http://localhost:8080/products
   Content-Type: application/json

   {
       "name": "Test Product",
       "description": "Description",
       "price": 99.99,
       "stock": 100
   }
   ```

3. Add to cart:

   ```http
   POST http://localhost:8080/cart/1/items?productId=1&quantity=2
   ```

4. Create order:

   ```http
   POST http://localhost:8080/orders?cartId=1
   ```

## Database Schema

The application uses the following tables:

- users
- products
- carts
- cart_items
- orders
- order_items