package com.stock.stock_broker.dto.transaction;

public class SellRequestDTO {
    private Long userId;
    private Long stockId;
    private Integer quantity;

    public SellRequestDTO(){}

    public SellRequestDTO(Long userId, Long stockId, Integer quantity){
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
