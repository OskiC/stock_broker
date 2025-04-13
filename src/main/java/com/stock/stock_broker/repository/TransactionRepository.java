package com.stock.stock_broker.repository;

import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.model.Transaction;
import com.stock.stock_broker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long id);

    List<Transaction> findByStatus(Transaction.TransactionStatus status);

    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserAndStockAndClosePriceIsNull(User user, Stock stock);
}
