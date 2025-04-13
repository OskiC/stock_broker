package com.stock.stock_broker.controller;

import com.stock.stock_broker.dto.transaction.BuyRequestDTO;
import com.stock.stock_broker.dto.transaction.HistoryResponseDTO;
import com.stock.stock_broker.dto.transaction.SellRequestDTO;
import com.stock.stock_broker.model.Transaction;
import com.stock.stock_broker.service.StockService;
import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private StockService stockService;

    @PostMapping("/buy")
    public ResponseEntity<Transaction> openTransaction(@RequestBody BuyRequestDTO buyRequestDTO){
        Stock stock = stockService.getStockById(buyRequestDTO.getStockId());
        Double openPrice = stock.getPrice();

        Transaction transaction = transactionService.openTransaction(
                buyRequestDTO.getUserId(),
                buyRequestDTO.getStockId(),
                openPrice,
                buyRequestDTO.getQuantity()
        );
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestBody SellRequestDTO sellRequestDTO){
        transactionService.sellStock(
                sellRequestDTO.getUserId(),
                sellRequestDTO.getStockId(),
                sellRequestDTO.getQuantity()
        );
        return ResponseEntity.ok("Stock sold successfully");
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<HistoryResponseDTO>> getUserTransactions(@PathVariable Long userId){
        List<HistoryResponseDTO> userTransactions = transactionService.getTransactionsByUser(userId);

        return ResponseEntity.ok(userTransactions);
    }
}
