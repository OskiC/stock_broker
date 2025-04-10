package com.stock.stock_broker.controller;

import com.stock.stock_broker.dto.TransactionDTO;
import com.stock.stock_broker.model.Transaction;
import com.stock.stock_broker.service.StockService;
import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private StockService stockService;

    @PostMapping("/buy")
    public ResponseEntity<Transaction> openTransaction(@RequestBody TransactionDTO transactionDTO){
        Stock stock = stockService.getStockById(transactionDTO.getStockId());
        Double openPrice = stock.getPrice();

        Transaction transaction = transactionService.openTransaction(
                transactionDTO.getUserId(),
                transactionDTO.getStockId(),
                openPrice,
                transactionDTO.getQuantity()
        );
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/sell/{transactionId}")
    public ResponseEntity<String> closeTransaction(@PathVariable Long transactionId){
        Transaction transaction = transactionService.getTransactionById(transactionId);

        Stock stock = stockService.getStockById(transaction.getId());
        Double closePrice = stock.getPrice();

        transactionService.closeTransaction(transactionId, closePrice);
        return ResponseEntity.ok("Transaction closed");
    }
}
