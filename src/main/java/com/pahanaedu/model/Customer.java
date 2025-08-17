package com.pahanaedu.model;

import java.sql.Timestamp;

public class Customer {
    private int accountNumber;
    private String name;
    private String address;
    private String telephone;
    private Timestamp createdAt;
    
    // Default constructor
    public Customer() {}
    
    // Constructor with parameters
    public Customer(String name, String address, String telephone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }
    
    // Getters and Setters
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
