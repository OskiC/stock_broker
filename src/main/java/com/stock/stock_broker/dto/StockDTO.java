package com.stock.stock_broker.dto;

public class StockDTO {
    private String stockName;
    private String codeName;
    private Double price;

    public StockDTO(){}

    public StockDTO(String stockName, String stockCodeName, Double stockPrice){
        this.stockName = stockName;
        this.codeName = stockCodeName;
        this.price = stockPrice;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
