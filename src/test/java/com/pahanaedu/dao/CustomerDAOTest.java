package com.pahanaedu.dao;

import com.pahanaedu.model.Customer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerDAOTest {

    private CustomerDAO customerDAO;
    
    @Mock
    private Connection mockConnection;
    
    @Mock
    private PreparedStatement mockPreparedStatement;
    
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        customerDAO = new CustomerDAO();
    }

    @Test
    @Order(1)
    @DisplayName("Test create customer successfully")
    void testCreateCustomer_Success() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setAddress("123 Test Street");
        customer.setTelephone("+94 11 123 4567");
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = customerDAO.addCustomer(customer);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(2)
    @DisplayName("Test create customer failure")
    void testCreateCustomer_Failure() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setAddress("123 Test Street");
        customer.setTelephone("+94 11 123 4567");
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = customerDAO.addCustomer(customer);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(3)
    @DisplayName("Test get customer by account number with valid number")
    void testGetCustomerByAccountNumber_ValidNumber() throws Exception {
        // Arrange
        int accountNumber = 1;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("account_number")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("address")).thenReturn("123 Test Street");
        when(mockResultSet.getString("telephone")).thenReturn("+94 11 123 4567");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2024-01-01 10:00:00"));
        
        // Act
        Customer result = customerDAO.getCustomerByAccountNumber(accountNumber);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getAccountNumber());
        assertEquals("John Doe", result.getName());
        assertEquals("123 Test Street", result.getAddress());
        assertEquals("+94 11 123 4567", result.getTelephone());
    }

    @Test
    @Order(4)
    @DisplayName("Test get customer by account number with invalid number")
    void testGetCustomerByAccountNumber_InvalidNumber() throws Exception {
        // Arrange
        int accountNumber = 999;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No customer found
        
        // Act
        Customer result = customerDAO.getCustomerByAccountNumber(accountNumber);
        
        // Assert
        assertNull(result);
    }

    @Test
    @Order(5)
    @DisplayName("Test get all customers")
    void testGetAllCustomers() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        // Mock multiple customers
        when(mockResultSet.next()).thenReturn(true, true, false); // Two customers
        when(mockResultSet.getInt("account_number")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("John Doe", "Jane Smith");
        when(mockResultSet.getString("address")).thenReturn("123 Test St", "456 Oak Ave");
        when(mockResultSet.getString("telephone")).thenReturn("+94 11 123 4567", "+94 11 234 5678");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2024-01-01 10:00:00"));
        
        // Act
        List<Customer> result = customerDAO.getAllCustomers();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    @Order(6)
    @DisplayName("Test update customer successfully")
    void testUpdateCustomer_Success() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setAccountNumber(1);
        customer.setName("Updated John Doe");
        customer.setAddress("456 Updated Street");
        customer.setTelephone("+94 11 987 6543");
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = customerDAO.updateCustomer(customer);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(7)
    @DisplayName("Test update customer failure")
    void testUpdateCustomer_Failure() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setAccountNumber(999);
        customer.setName("Non-existent Customer");
        customer.setAddress("Nowhere");
        customer.setTelephone("0000000000");
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = customerDAO.updateCustomer(customer);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(8)
    @DisplayName("Test delete customer successfully")
    void testDeleteCustomer_Success() throws Exception {
        // Arrange
        int accountNumber = 1;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = customerDAO.deleteCustomer(accountNumber);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(9)
    @DisplayName("Test delete customer failure")
    void testDeleteCustomer_Failure() throws Exception {
        // Arrange
        int accountNumber = 999;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = customerDAO.deleteCustomer(accountNumber);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(10)
    @DisplayName("Test search customers by name")
    void testSearchCustomersByName() throws Exception {
        // Arrange
        String searchTerm = "John";
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        // Mock search results
        when(mockResultSet.next()).thenReturn(true, false); // One customer found
        when(mockResultSet.getInt("account_number")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("address")).thenReturn("123 Test Street");
        when(mockResultSet.getString("telephone")).thenReturn("+94 11 123 4567");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2024-01-01 10:00:00"));
        
        // Act
        List<Customer> result = customerDAO.searchCustomers(searchTerm);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains(searchTerm));
    }

    @Test
    @Order(11)
    @DisplayName("Test database connection exception handling")
    void testDatabaseConnectionException() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));
        
        // Act & Assert
        assertThrows(Exception.class, () -> {
            customerDAO.getAllCustomers();
        });
    }

    @Test
    @Order(12)
    @DisplayName("Test customer with null values")
    void testCustomerWithNullValues() throws Exception {
        // Arrange
        Customer customer = new Customer();
        customer.setName(null);
        customer.setAddress(null);
        customer.setTelephone(null);
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = customerDAO.addCustomer(customer);
        
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
