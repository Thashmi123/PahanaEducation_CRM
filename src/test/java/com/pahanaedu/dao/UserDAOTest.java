package com.pahanaedu.dao;

import com.pahanaedu.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOTest {

    private UserDAO userDAO;
    
    @Mock
    private Connection mockConnection;
    
    @Mock
    private PreparedStatement mockPreparedStatement;
    
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    @Order(1)
    @DisplayName("Test user authentication with valid credentials")
    void testAuthenticateUser_ValidCredentials() throws Exception {
        // Arrange
        String username = "admin";
        String password = "admin123";
        
        // Mock the database connection and result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("user_id")).thenReturn(1);
        when(mockResultSet.getString("username")).thenReturn("admin");
        when(mockResultSet.getString("role")).thenReturn("ADMIN");
        
        // Act
        User result = userDAO.authenticateUser(username, password);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals("admin", result.getUsername());
        assertEquals("ADMIN", result.getRole());
    }

    @Test
    @Order(2)
    @DisplayName("Test user authentication with invalid credentials")
    void testAuthenticateUser_InvalidCredentials() throws Exception {
        // Arrange
        String username = "invalid";
        String password = "wrongpassword";
        
        // Mock the database connection and result
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No user found
        
        // Act
        User result = userDAO.authenticateUser(username, password);
        
        // Assert
        assertNull(result);
    }

    @Test
    @Order(3)
    @DisplayName("Test add user successfully")
    void testAddUser_Success() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("newpass123");
        user.setRole("CASHIER");
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = userDAO.addUser(user);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(4)
    @DisplayName("Test add user failure")
    void testAddUser_Failure() throws Exception {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        user.setPassword("newpass123");
        user.setRole("CASHIER");
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows affected
        
        // Act
        boolean result = userDAO.addUser(user);
        
        // Assert
        assertFalse(result);
    }

    @Test
    @Order(5)
    @DisplayName("Test update user successfully")
    void testUpdateUser_Success() throws Exception {
        // Arrange
        User user = new User();
        user.setUserId(1);
        user.setUsername("updateduser");
        user.setPassword("updatedpass");
        user.setRole("ADMIN");
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = userDAO.updateUser(user);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(6)
    @DisplayName("Test delete user successfully")
    void testDeleteUser_Success() throws Exception {
        // Arrange
        int userId = 1;
        
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row affected
        
        // Act
        boolean result = userDAO.deleteUser(userId);
        
        // Assert
        assertTrue(result);
    }

    @Test
    @Order(7)
    @DisplayName("Test database connection exception handling")
    void testDatabaseConnectionException() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection failed"));
        
        // Act & Assert
        assertThrows(Exception.class, () -> {
            userDAO.authenticateUser("test", "test");
        });
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
