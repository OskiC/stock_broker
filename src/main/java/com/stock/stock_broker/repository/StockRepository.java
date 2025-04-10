package com.stock.stock_broker.repository;

import com.stock.stock_broker.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findById(Long id);
    Optional<Stock> findByName(String stockName);
    Optional<Stock> findByCodeName(String codeName);
    boolean stockExists(String stockName, String codeName);
}
