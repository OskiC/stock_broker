package com.stock.stock_broker.controller;

import com.stock.stock_broker.dto.UserRegistrationDTO;
import com.stock.stock_broker.model.User;
import com.stock.stock_broker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO userDTO){
        User newUser = userService.registerUser(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        User userFound = userService.getUserById(userId);
        return ResponseEntity.ok(userFound);
    }
}
