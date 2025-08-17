package com.pahanaedu.dao;

import com.pahanaedu.model.Bill;
import com.pahanaedu.model.BillItem;
import com.pahanaedu.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    
    public boolean saveBill(Bill bill) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // Insert bill
            String billSql = "INSERT INTO bills (account_number, user_id, total_amount) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, bill.getAccountNumber());
            pstmt.setInt(2, bill.getUserId());
            pstmt.setBigDecimal(3, bill.getTotalAmount());
            
            int billResult = pstmt.executeUpdate();
            if (billResult == 0) {
                conn.rollback();
                return false;
            }
            
            // Get generated bill ID
            rs = pstmt.getGeneratedKeys();
            int billId = 0;
            if (rs.next()) {
                billId = rs.getInt(1);
            } else {
                conn.rollback();
                return false;
            }
            
            // Insert bill items
            String itemSql = "INSERT INTO bill_items (bill_id, item_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(itemSql);
            
            for (BillItem item : bill.getBillItems()) {
                pstmt.setInt(1, billId);
                pstmt.setInt(2, item.getItemId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setBigDecimal(4, item.getUnitPrice());
                
                int itemResult = pstmt.executeUpdate();
                if (itemResult == 0) {
                    conn.rollback();
                    return false;
                }
            }
            
            // Update stock quantities
            String updateStockSql = "UPDATE items SET stock_quantity = stock_quantity - ? WHERE item_id = ?";
            pstmt = conn.prepareStatement(updateStockSql);
            
            for (BillItem item : bill.getBillItems()) {
                pstmt.setInt(1, item.getQuantity());
                pstmt.setInt(2, item.getItemId());
                
                int stockResult = pstmt.executeUpdate();
                if (stockResult == 0) {
                    conn.rollback();
                    return false;
                }
            }
            
            conn.commit(); // Commit transaction
            return true;
            
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback on error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public List<Bill> getBillsByCustomer(int accountNumber) {
        List<Bill> bills = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT b.*, u.username FROM bills b " +
                        "JOIN users u ON b.user_id = u.user_id " +
                        "WHERE b.account_number = ? " +
                        "ORDER BY b.bill_date DESC";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountNumber);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAccountNumber(rs.getInt("account_number"));
                bill.setUserId(rs.getInt("user_id"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                
                // Get bill items for this bill
                bill.setBillItems(getBillItemsByBillId(bill.getBillId()));
                
                bills.add(bill);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return bills;
    }
    
    public List<BillItem> getBillItemsByBillId(int billId) {
        List<BillItem> items = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT bi.*, i.item_name FROM bill_items bi " +
                        "JOIN items i ON bi.item_id = i.item_id " +
                        "WHERE bi.bill_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, billId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBillItemId(rs.getInt("bill_item_id"));
                item.setBillId(rs.getInt("bill_id"));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getBigDecimal("unit_price"));
                item.setSubtotal(rs.getBigDecimal("subtotal"));
                item.setItemName(rs.getString("item_name"));
                
                items.add(item);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return items;
    }
    
    public Bill getBillById(int billId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM bills WHERE bill_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, billId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAccountNumber(rs.getInt("account_number"));
                bill.setUserId(rs.getInt("user_id"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                
                // Get bill items
                bill.setBillItems(getBillItemsByBillId(billId));
                
                return bill;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT b.*, u.username, c.name as customer_name FROM bills b " +
                        "JOIN users u ON b.user_id = u.user_id " +
                        "JOIN customers c ON b.account_number = c.account_number " +
                        "ORDER BY b.bill_date DESC";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAccountNumber(rs.getInt("account_number"));
                bill.setUserId(rs.getInt("user_id"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                
                bills.add(bill);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return bills;
    }
    
    public boolean deleteBill(int billId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Delete bill items first (due to foreign key constraint)
            String deleteItemsSql = "DELETE FROM bill_items WHERE bill_id = ?";
            pstmt = conn.prepareStatement(deleteItemsSql);
            pstmt.setInt(1, billId);
            pstmt.executeUpdate();
            
            // Delete bill
            String deleteBillSql = "DELETE FROM bills WHERE bill_id = ?";
            pstmt = conn.prepareStatement(deleteBillSql);
            pstmt.setInt(1, billId);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
