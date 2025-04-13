package com.stock.stock_broker.controller;

import com.stock.stock_broker.dto.user.BalanceDTO;
import com.stock.stock_broker.dto.user.PortfolioDTO;
import com.stock.stock_broker.dto.user.UserRegistrationDTO;
import com.stock.stock_broker.model.User;
import com.stock.stock_broker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/deposit")
    public ResponseEntity<User> deposit(@RequestBody BalanceDTO balanceDTO){
        User user = userService.deposit(balanceDTO.getUserId(), balanceDTO.getAmount());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<User> withdraw(@RequestBody BalanceDTO balanceDTO){
        User user = userService.withdraw(balanceDTO.getUserId(), balanceDTO.getAmount());

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        User userFound = userService.getUserById(userId);
        return ResponseEntity.ok(userFound);
    }

    @GetMapping("/{userId}/portfolio")
    public ResponseEntity<List<PortfolioDTO>> getPortfolio(@PathVariable Long userId){
        List<PortfolioDTO> portfolio = userService.getUserPortfolio(userId);
        return ResponseEntity.ok(portfolio);
    }
}
