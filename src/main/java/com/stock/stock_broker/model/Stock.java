package com.stock.stock_broker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stock {
    @Id
    private Long id;
    private String stockName;
    private String codeName;
    private Float price;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
