package com.stock.stock_broker.controller;

import com.stock.stock_broker.dto.StockDTO;
import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.service.StockService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<Stock> createStock(@RequestBody StockDTO stockDTO){
        Stock newStock = stockService.createStock(stockDTO.getStockName(), stockDTO.getCodeName()
                , stockDTO.getPrice());
        return ResponseEntity.ok(newStock);
    }

    @GetMapping("/name/{stockName}")
    public ResponseEntity<Stock> getStockByName(@PathVariable String stockName){
        Stock stockFound = stockService.getStockByName(stockName);
        return ResponseEntity.ok(stockFound);
    }

    @GetMapping("/code/{codeName}")
    public ResponseEntity<Stock> getStockByCode(@PathVariable String codeName){
        Stock stockFound = stockService.getStockByCodeName(codeName);
        return ResponseEntity.ok(stockFound);
    }
}
