package com.stock.stock_broker.dto.transaction;

import com.stock.stock_broker.model.Transaction;

import java.time.LocalDateTime;

public class HistoryResponseDTO {
    private String stockName;
    private Transaction.TransactionStatus status;
    private String stockSymbol;
    private Double PNL;
    private LocalDateTime timestamp;

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public Double getPNL() {
        return PNL;
    }

    public void setPNL(Double PNL) {
        this.PNL = PNL;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Transaction.TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(Transaction.TransactionStatus status) {
        this.status = status;
    }
}
