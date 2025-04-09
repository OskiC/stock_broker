package com.stock.stock_broker.service;

import com.stock.stock_broker.model.User;
import com.stock.stock_broker.model.Transaction;
import com.stock.stock_broker.repository.TransactionRepository;
import com.stock.stock_broker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public void closeTransaction(Long transactionId, Double closePrice){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(()
            -> new IllegalArgumentException("Transaction not found"));

        transaction.closeTransaction(closePrice);
        User user = transaction.getUser();

        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionByUser(Long userId){
        return transactionRepository.findByUserId(userId);
    }
}
