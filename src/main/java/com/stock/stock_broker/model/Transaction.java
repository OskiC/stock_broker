package com.stock.stock_broker.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "open_price", nullable = false)
    private Double openPrice;

    @Column(name = "close_price", nullable = true)
    private Double closePrice;

    @Column(name = "quanitity", nullable = false)
    private Integer quantity;

    @Column(name = "transaction_status", nullable = false)
    private TransactionStatus status;

    @Column(name = "open_date", nullable = false)
    private LocalDateTime openDate;

    @Column(name = "close_date", nullable = true)
    private LocalDateTime closeDate;

    @Column(name = "profit_and_loss", nullable = true)
    private Double profitAndLoss;

    public Transaction(){}

    public Transaction(User user, Stock stock, Double openPrice, Integer quantity){
        this.user = user;
        this.stock = stock;
        this.openPrice = openPrice;
        this.quantity = quantity;
        this.status = TransactionStatus.OPEN;
        this.openDate = LocalDateTime.now();
    }

    public void closeTransaction(Double closePrice){
        if(this.status == TransactionStatus.OPEN) {
            this.closeDate = LocalDateTime.now();
            this.status = TransactionStatus.CLOSED;
            this.profitAndLoss = calculateDifference();
            this.closePrice = closePrice;
        }
        else{
            throw new IllegalStateException("Cannot close transaction that is already closed!");
        }

    }

    public Double calculateDifference(){
        if(closePrice != null){
            return (openPrice - closePrice)*quantity;
        }
        return 0.0;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public Double getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(Double profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public enum TransactionStatus{
        OPEN,
        CLOSED
    }

}


