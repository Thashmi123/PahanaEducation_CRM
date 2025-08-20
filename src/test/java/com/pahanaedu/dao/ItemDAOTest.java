package com.pahanaedu.dao;

import com.pahanaedu.model.Item;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemDAOTest {

    private ItemDAO itemDAO;
    
    @Mock
    private Connection mockConnection;
    
    @Mock
    private PreparedStatement mockPreparedStatement;
    
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        itemDAO = new ItemDAO();
    }

    @Test
    @Order(1)
    @DisplayName("Test create item successfully")
    void testCreateItem_Success() throws Exception {
        // Arrange
        Item item = new Item();
        item.setItemName("Test Book");
        item.setPrice(new BigDecimal("25.99"));
        item.setStockQuantity(50);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = itemDAO.addItem(item);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(2)
    @DisplayName("Test create item failure")
    void testCreateItem_Failure() throws Exception {
        // Arrange
        Item item = new Item();
        item.setItemName("Test Book");
        item.setPrice(new BigDecimal("25.99"));
        item.setStockQuantity(50);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = itemDAO.addItem(item);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(3)
    @DisplayName("Test get item by ID with valid ID")
    void testGetItemById_ValidId() throws Exception {
        // Arrange
        int itemId = 1;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("item_id")).thenReturn(1);
        when(mockResultSet.getString("item_name")).thenReturn("Test Book");
        when(mockResultSet.getBigDecimal("price")).thenReturn(new BigDecimal("25.99"));
        when(mockResultSet.getInt("stock_quantity")).thenReturn(50);
        when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2024-01-01 10:00:00"));
        
        // Act
        Item result = itemDAO.getItemById(itemId);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getItemId());
        assertEquals("Test Book", result.getItemName());
        assertEquals(new BigDecimal("25.99"), result.getPrice());
        assertEquals(50, result.getStockQuantity());
    }

    @Test
    @Order(4)
    @DisplayName("Test get item by ID with invalid ID")
    void testGetItemById_InvalidId() throws Exception {
        // Arrange
        int itemId = 999;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No item found
        
        // Act
        Item result = itemDAO.getItemById(itemId);
        
        // Assert
        assertNull(result);
    }

    @Test
    @Order(5)
    @DisplayName("Test get all items")
    void testGetAllItems() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        // Mock multiple items
        when(mockResultSet.next()).thenReturn(true, true, false); // Two items
        when(mockResultSet.getInt("item_id")).thenReturn(1, 2);
        when(mockResultSet.getString("item_name")).thenReturn("Book 1", "Book 2");
        when(mockResultSet.getBigDecimal("price")).thenReturn(new BigDecimal("25.99"), new BigDecimal("30.00"));
        when(mockResultSet.getInt("stock_quantity")).thenReturn(50, 75);
        when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2024-01-01 10:00:00"));
        
        // Act
        List<Item> result = itemDAO.getAllItems();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getItemName());
        assertEquals("Book 2", result.get(1).getItemName());
    }

    @Test
    @Order(6)
    @DisplayName("Test update item successfully")
    void testUpdateItem_Success() throws Exception {
        // Arrange
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("Updated Book");
        item.setPrice(new BigDecimal("29.99"));
        item.setStockQuantity(60);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = itemDAO.updateItem(item);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(7)
    @DisplayName("Test update item failure")
    void testUpdateItem_Failure() throws Exception {
        // Arrange
        Item item = new Item();
        item.setItemId(999);
        item.setItemName("Non-existent Item");
        item.setPrice(new BigDecimal("0.00"));
        item.setStockQuantity(0);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = itemDAO.updateItem(item);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(8)
    @DisplayName("Test delete item successfully")
    void testDeleteItem_Success() throws Exception {
        // Arrange
        int itemId = 1;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = itemDAO.deleteItem(itemId);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(9)
    @DisplayName("Test delete item failure")
    void testDeleteItem_Failure() throws Exception {
        // Arrange
        int itemId = 999;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = itemDAO.deleteItem(itemId);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(10)
    @DisplayName("Test search items by name")
    void testSearchItemsByName() throws Exception {
        // Arrange
        String searchTerm = "Book";
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        // Mock search results
        when(mockResultSet.next()).thenReturn(true, true, false); // Two items found
        when(mockResultSet.getInt("item_id")).thenReturn(1, 2);
        when(mockResultSet.getString("item_name")).thenReturn("Math Book", "Science Book");
        when(mockResultSet.getBigDecimal("price")).thenReturn(new BigDecimal("25.99"), new BigDecimal("30.00"));
        when(mockResultSet.getInt("stock_quantity")).thenReturn(50, 75);
        when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2024-01-01 10:00:00"));
        
        // Act
        List<Item> result = itemDAO.searchItems(searchTerm);
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getItemName().contains(searchTerm));
        assertTrue(result.get(1).getItemName().contains(searchTerm));
    }

    @Test
    @Order(11)
    @DisplayName("Test update stock quantity successfully")
    void testUpdateStockQuantity_Success() throws Exception {
        // Arrange
        int itemId = 1;
        int newQuantity = 45;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = itemDAO.updateStockQuantity(itemId, newQuantity);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(12)
    @DisplayName("Test update stock quantity failure")
    void testUpdateStockQuantity_Failure() throws Exception {
        // Arrange
        int itemId = 999;
        int newQuantity = 45;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = itemDAO.updateStockQuantity(itemId, newQuantity);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(13)
    @DisplayName("Test database connection exception handling")
    void testDatabaseConnectionException() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));
        
        // Act & Assert
        assertThrows(Exception.class, () -> {
            itemDAO.getAllItems();
        });
    }

    @Test
    @Order(14)
    @DisplayName("Test item with null values")
    void testItemWithNullValues() throws Exception {
        // Arrange
        Item item = new Item();
        item.setItemName(null);
        item.setPrice(null);
        item.setStockQuantity(0);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = itemDAO.addItem(item);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(15)
    @DisplayName("Test item with zero price")
    void testItemWithZeroPrice() throws Exception {
        // Arrange
        Item item = new Item();
        item.setItemName("Free Book");
        item.setPrice(BigDecimal.ZERO);
        item.setStockQuantity(100);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = itemDAO.addItem(item);
        
        // Assert
        assertTrue(result);
    }

    @AfterEach
    void tearDown() {
        // Clean up resources
        try {
            if (mockResultSet != null) mockResultSet.close();
            if (mockPreparedStatement != null) mockPreparedStatement.close();
            if (mockConnection != null) mockConnection.close();
        } catch (SQLException e) {
            // Ignore cleanup errors in tests
        }
    }
}
