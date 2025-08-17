package com.pahanaedu.model;

import java.math.BigDecimal;

public class BillItem {
    private int billItemId;
    private int billId;
    private int itemId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String itemName; // For display purposes
    
    // Default constructor
    public BillItem() {}
    
    // Constructor with parameters
    public BillItem(int itemId, int quantity, BigDecimal unitPrice) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
    
    // Getters and Setters
    public int getBillItemId() {
        return billItemId;
    }
    
    public void setBillItemId(int billItemId) {
        this.billItemId = billItemId;
    }
    
    public int getBillId() {
        return billId;
    }
    
    public void setBillId(int billId) {
        this.billId = billId;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (this.unitPrice != null) {
            this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        if (this.quantity > 0) {
            this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
