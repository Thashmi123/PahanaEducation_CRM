package com.pahanaedu.dao;

import com.pahanaedu.model.Item;
import com.pahanaedu.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (item_name, price, stock_quantity) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, item.getItemName());
            ps.setBigDecimal(2, item.getPrice());
            ps.setInt(3, item.getStockQuantity());
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET item_name = ?, price = ?, stock_quantity = ? WHERE item_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, item.getItemName());
            ps.setBigDecimal(2, item.getPrice());
            ps.setInt(3, item.getStockQuantity());
            ps.setInt(4, item.getItemId());
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, itemId);
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Item getItemById(int itemId) {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, itemId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setStockQuantity(rs.getInt("stock_quantity"));
                    item.setCreatedAt(rs.getTimestamp("created_at"));
                    return item;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM items ORDER BY item_name";
        List<Item> items = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                item.setCreatedAt(rs.getTimestamp("created_at"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public List<Item> searchItems(String searchTerm) {
        String sql = "SELECT * FROM items WHERE item_name LIKE ? ORDER BY item_name";
        List<Item> items = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setItemId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setStockQuantity(rs.getInt("stock_quantity"));
                    item.setCreatedAt(rs.getTimestamp("created_at"));
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public boolean updateStockQuantity(int itemId, int newQuantity) {
        String sql = "UPDATE items SET stock_quantity = ? WHERE item_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, newQuantity);
            ps.setInt(2, itemId);
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
