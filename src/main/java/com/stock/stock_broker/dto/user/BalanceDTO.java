package com.stock.stock_broker.dto.user;

public class BalanceDTO {
    private Long userId;
    private Double amount;

    BalanceDTO(){}
    BalanceDTO(Long userId, Double depositOrWithdrawVal){
        this.userId = userId;
        this.amount = depositOrWithdrawVal;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
