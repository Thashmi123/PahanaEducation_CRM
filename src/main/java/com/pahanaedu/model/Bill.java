package com.pahanaedu.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Bill {
    private int billId;
    private int accountNumber;
    private int userId;
    private Timestamp billDate;
    private BigDecimal totalAmount;
    private List<BillItem> billItems;
    
    // Default constructor
    public Bill() {}
    
    // Constructor with parameters
    public Bill(int accountNumber, int userId) {
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.billDate = new Timestamp(System.currentTimeMillis());
        this.totalAmount = BigDecimal.ZERO;
    }
    
    // Getters and Setters
    public int getBillId() {
        return billId;
    }
    
    public void setBillId(int billId) {
        this.billId = billId;
    }
    
    public int getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public Timestamp getBillDate() {
        return billDate;
    }
    
    public void setBillDate(Timestamp billDate) {
        this.billDate = billDate;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public List<BillItem> getBillItems() {
        return billItems;
    }
    
    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }
    
    // Calculate total amount from bill items
    public void calculateTotal() {
        if (billItems != null) {
            this.totalAmount = billItems.stream()
                .map(BillItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
}
