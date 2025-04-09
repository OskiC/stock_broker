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

    public enum TransactionStatus{
        OPEN,
        CLOSED
    }

}


