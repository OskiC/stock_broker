package com.stock.stock_broker.dto;

public class StockDTO {
    private String stockName;
    private String stockCodeName;
    private Double stockPrice;

    public StockDTO(){}

    public StockDTO(String stockName, String stockCodeName, Double stockPrice){
        this.stockName = stockName;
        this.stockCodeName = stockCodeName;
        this.stockPrice = stockPrice;
    }


    public String getStockCodeName() {
        return stockCodeName;
    }

    public void setStockCodeName(String stockCodeName) {
        this.stockCodeName = stockCodeName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getStockPrice(){
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice){
        this.stockPrice = stockPrice;
    }
}
