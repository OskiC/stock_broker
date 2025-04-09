package com.stock.stock_broker.repository;

import com.stock.stock_broker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findById(Long Id);

    List<Transaction> findByStatus(Transaction.TransactionStatus status);

    List<Transaction> findByUserId(Long userId);
}
