package com.stock.stock_broker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    //FOR TESTS PURPOSE
    //[TODO]:
    //Security measures
    @Id
    private Long id;
    private char[] password;
    private String name;
    private String surname;
    private Float balance;

    public User(char[] pass, String name, String surname, Float balance){
        this.password = pass;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public User() {

    }


    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
