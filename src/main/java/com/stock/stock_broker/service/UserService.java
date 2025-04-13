package com.stock.stock_broker.service;

import com.stock.stock_broker.dto.user.PortfolioDTO;
import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.model.Transaction;
import com.stock.stock_broker.model.User;
import com.stock.stock_broker.repository.TransactionRepository;
import com.stock.stock_broker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private TransactionRepository transactionRepository;

    public User registerUser(String username, String password){
        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("username is taken");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setBalance(0.0);

        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User deposit(Long userId, Double value){
        if(value == null || value <= 0){
            throw new IllegalArgumentException("Value must be greater than zero");
        }

        User user = getUserById(userId);
        user.setBalance(user.getBalance() + value);

        return userRepository.save(user);
    }

    public User withdraw(Long userId, Double value){
        if(value == null || value <= 0){
            throw new IllegalArgumentException("Value must be greater than 0");
        }

        User user = getUserById(userId);

        if(user.getBalance() < value){
            throw new IllegalArgumentException("Insufficient balance for withdrawal");
        }
        if(user.getBalance() >= value) {
            user.setBalance(user.getBalance() - value);
        }

        return userRepository.save(user);
    }

    public List<PortfolioDTO> getUserPortfolio(Long id){
        User user = getUserById(id);

        List<Transaction> transactions = transactionRepository.findByUserId(id);

        Map<Stock, List<Transaction>> grouped = transactions.stream()
                .filter(t -> t.getClosePrice() == null).collect(Collectors.groupingBy(Transaction::getStock));

        List<PortfolioDTO> result = new ArrayList<>();

        for(Map.Entry<Stock, List<Transaction>> entry : grouped.entrySet()){
            Stock stock = entry.getKey();
            List<Transaction> userTransactions = entry.getValue();

            int quantity = userTransactions.stream().mapToInt(Transaction::getQuantity).sum();
            double avgBuyPrice = userTransactions.stream().
                    mapToDouble(t -> t.getOpenPrice() * t.getQuantity()).sum() / quantity;
            double currentPrice = stock.getPrice();
            double totalValue = stock.getPrice() * quantity;
            double PnL = quantity * (currentPrice - avgBuyPrice);

            result.add(new PortfolioDTO(
                    stock.getStockName(),
                    quantity,
                    avgBuyPrice,
                    currentPrice,
                    totalValue,
                    PnL
            ));
        }
        return result;
    }
}
