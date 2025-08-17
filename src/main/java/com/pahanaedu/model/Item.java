package com.pahanaedu.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Item {
    private int itemId;
    private String itemName;
    private BigDecimal price;
    private int stockQuantity;
    private Timestamp createdAt;
    
    // Default constructor
    public Item() {}
    
    // Constructor with parameters
    public Item(String itemName, BigDecimal price, int stockQuantity) {
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    // Getters and Setters
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public int getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
