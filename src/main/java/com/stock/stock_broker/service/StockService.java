package com.stock.stock_broker.service;

import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Stock createStock(String stockName, String stockCode, Double price){
        if(stockRepository.existsByStockNameAndCodeName(stockName, stockCode)){
            throw new IllegalArgumentException("Stock already exists");
        }

        Stock stock = new Stock();
        stock.setStockName(stockName);
        stock.setCodeName(stockCode);
        stock.setPrice(price);

        return stockRepository.save(stock);
    }

    public Stock getStockByName(String stockName){
        return stockRepository.findByStockName(stockName)
                .orElseThrow(() -> new IllegalArgumentException("Stock with that name not found!"));
    }

    public Stock getStockByCodeName(String codeName){
        return stockRepository.findByCodeName(codeName)
                .orElseThrow(() -> new IllegalArgumentException("Stock with that codeName not found!"));
    }

    public Stock getStockById(Long id){
        return stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock with that ID not found!"));
    }
}
