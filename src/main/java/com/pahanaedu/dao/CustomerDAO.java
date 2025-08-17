package com.pahanaedu.dao;

import com.pahanaedu.model.Customer;
import com.pahanaedu.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, address, telephone) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getTelephone());
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, address = ?, telephone = ? WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getTelephone());
            ps.setInt(4, customer.getAccountNumber());
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteCustomer(int accountNumber) {
        String sql = "DELETE FROM customers WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, accountNumber);
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Customer getCustomerByAccountNumber(int accountNumber) {
        String sql = "SELECT * FROM customers WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, accountNumber);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setAccountNumber(rs.getInt("account_number"));
                    customer.setName(rs.getString("name"));
                    customer.setAddress(rs.getString("address"));
                    customer.setTelephone(rs.getString("telephone"));
                    customer.setCreatedAt(rs.getTimestamp("created_at"));
                    return customer;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers ORDER BY account_number";
        List<Customer> customers = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setAccountNumber(rs.getInt("account_number"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setCreatedAt(rs.getTimestamp("created_at"));
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    public List<Customer> searchCustomers(String searchTerm) {
        String sql = "SELECT * FROM customers WHERE name LIKE ? OR address LIKE ? OR telephone LIKE ? ORDER BY account_number";
        List<Customer> customers = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setAccountNumber(rs.getInt("account_number"));
                    customer.setName(rs.getString("name"));
                    customer.setAddress(rs.getString("address"));
                    customer.setTelephone(rs.getString("telephone"));
                    customer.setCreatedAt(rs.getTimestamp("created_at"));
                    customers.add(customer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
}
