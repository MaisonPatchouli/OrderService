package org.example.orderservice.controller;

import org.example.orderservice.entity.UserEntity;
import org.example.orderservice.service.UserService;
import org.example.orderservice.service.UserRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;
    private final UserRegistrationService userRegistrationService;

    public UserController(UserService userService, UserRegistrationService userRegistrationService) {
        this.userService = userService;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        System.out.println("Getting all users");
        return userService.getAllUsers();
    }

    @GetMapping("/{email}")
    public UserEntity getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userRegistrationService.registerUser(user));
    }
} 