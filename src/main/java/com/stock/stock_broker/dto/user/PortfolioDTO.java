package com.stock.stock_broker.dto.user;

public class PortfolioDTO {
    private String stockName;
    private Integer quantity;
    private Double avgBuyPrice;
    private Double currentPrice;
    private Double totalValue;
    private Double PnL;

    public PortfolioDTO(){}


    public PortfolioDTO(String stockName, Integer quantity, Double avgBuyPrice, Double currentPrice, Double totalValue, Double pnL) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
        this.currentPrice = currentPrice;
        this.totalValue = totalValue;
        PnL = pnL;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getAvgBuyPrice() {
        return avgBuyPrice;
    }

    public void setAvgBuyPrice(Double avgBuyPrice) {
        this.avgBuyPrice = avgBuyPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getPnL() {
        return PnL;
    }

    public void setPnL(Double pnL) {
        PnL = pnL;
    }
}
