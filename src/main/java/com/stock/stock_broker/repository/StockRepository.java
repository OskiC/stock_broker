package com.stock.stock_broker.repository;

import com.stock.stock_broker.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findById(Long id);
    Optional<Stock> findByStockName(String stockName);
    Optional<Stock> findByCodeName(String codeName);
    boolean existsByStockNameAndCodeName(String stockName, String codeName);
}
