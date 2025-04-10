package com.stock.stock_broker.dto;

public class TransactionDTO {
    private Long userId;
    private Long stockId;
    private Double openPrice;
    private Double closePrice;
    private Integer quantity;

    TransactionDTO(){}

    //DTO for openingTransaction
    TransactionDTO(Long userId, Long stockId, Double openPrice, Integer quantity){
        this.userId = userId;
        this.stockId = stockId;
        this.openPrice = openPrice;
        this.quantity = quantity;
    }

    //DTO for closingTransaction
    TransactionDTO(Double closePrice){
        this.closePrice = closePrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
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
}
