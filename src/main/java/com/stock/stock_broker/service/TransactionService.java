package com.stock.stock_broker.service;

import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.model.User;
import com.stock.stock_broker.model.Transaction;
import com.stock.stock_broker.repository.StockRepository;
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
    @Autowired
    private StockRepository stockRepository;

    public Transaction openTransaction(Long userId, Long stockId, Double openPrice, Integer quantity){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("Stock not found"));

        Transaction transaction = new Transaction(user, stock, openPrice, quantity);
        return transactionRepository.save(transaction);
    }

    public void closeTransaction(Long transactionId, Double closePrice){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(()
            -> new IllegalArgumentException("Transaction not found"));

        transaction.closeTransaction(closePrice);
        User user = transaction.getUser();
        user.setBalance(user.getBalance() + transaction.getProfitAndLoss());
        userRepository.save(user);

        transactionRepository.save(transaction);
    }

    public List<Transaction> getClosedTransactions(Long userId){
        return transactionRepository.findByStatus(Transaction.TransactionStatus.CLOSED);
    }

    public List<Transaction> getTransactionByUser(Long userId){
        return transactionRepository.findByUserId(userId);
    }
}
