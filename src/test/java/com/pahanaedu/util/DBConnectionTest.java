package com.pahanaedu.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBConnectionTest {

    @Test
    @Order(1)
    @DisplayName("Test database connection properties")
    void testDatabaseConnectionProperties() throws Exception {
        // This test verifies that the connection properties are correctly configured
        // Note: We can't actually test the connection without a real database
        
        // Verify the constants are properly defined
        assertNotNull(DBConnection.class.getDeclaredField("URL"));
        assertNotNull(DBConnection.class.getDeclaredField("USER"));
        assertNotNull(DBConnection.class.getDeclaredField("PASSWORD"));
        
        // Verify the method exists
        assertNotNull(DBConnection.class.getDeclaredMethod("getConnection"));
    }

    @Test
    @Order(2)
    @DisplayName("Test database connection method signature")
    void testGetConnectionMethodSignature() throws NoSuchMethodException {
        // Verify the method signature
        var method = DBConnection.class.getDeclaredMethod("getConnection");
        
        assertEquals(Connection.class, method.getReturnType());
        assertTrue(method.getExceptionTypes().length > 0);
        
        // Check if it throws Exception
        boolean throwsException = false;
        for (Class<?> exceptionType : method.getExceptionTypes()) {
            if (Exception.class.isAssignableFrom(exceptionType)) {
                throwsException = true;
                break;
            }
        }
        assertTrue(throwsException, "Method should throw Exception");
    }

    @Test
    @Order(3)
    @DisplayName("Test database URL format")
    void testDatabaseURLFormat() {
        // This test verifies the URL format is correct
        // We can't access private fields directly, but we can verify the format through reflection
        
        try {
            var urlField = DBConnection.class.getDeclaredField("URL");
            urlField.setAccessible(true);
            String url = (String) urlField.get(null);
            
            // Verify URL format
            assertTrue(url.startsWith("jdbc:mysql://"), "URL should start with jdbc:mysql://");
            assertTrue(url.contains("127.0.0.1:3306"), "URL should contain localhost and port");
            assertTrue(url.contains("pahanaedu"), "URL should contain database name");
            assertTrue(url.contains("useSSL=false"), "URL should disable SSL");
            assertTrue(url.contains("serverTimezone=UTC"), "URL should set timezone");
            
        } catch (Exception e) {
            fail("Failed to access URL field: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test database credentials")
    void testDatabaseCredentials() {
        // This test verifies the database credentials are set
        try {
            var userField = DBConnection.class.getDeclaredField("USER");
            var passwordField = DBConnection.class.getDeclaredField("PASSWORD");
            
            userField.setAccessible(true);
            passwordField.setAccessible(true);
            
            String user = (String) userField.get(null);
            String password = (String) passwordField.get(null);
            
            // Verify credentials are not null or empty
            assertNotNull(user, "Database user should not be null");
            assertNotNull(password, "Database password should not be null");
            assertFalse(user.trim().isEmpty(), "Database user should not be empty");
            assertFalse(password.trim().isEmpty(), "Database password should not be empty");
            
        } catch (Exception e) {
            fail("Failed to access credential fields: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test class instantiation")
    void testClassInstantiation() {
        // Verify the class can be instantiated
        assertDoesNotThrow(() -> {
            DBConnection dbConnection = new DBConnection();
            assertNotNull(dbConnection);
        });
    }

    @Test
    @Order(6)
    @DisplayName("Test class structure")
    void testClassStructure() {
        // Verify the class has the expected structure
        Class<?> clazz = DBConnection.class;
        
        // Check if it's public
        assertTrue(java.lang.reflect.Modifier.isPublic(clazz.getModifiers()), 
                  "Class should be public");
        
        // Check if it has the expected fields
        assertTrue(hasField(clazz, "URL"), "Class should have URL field");
        assertTrue(hasField(clazz, "USER"), "Class should have USER field");
        assertTrue(hasField(clazz, "PASSWORD"), "Class should have PASSWORD field");
        
        // Check if it has the expected method
        assertTrue(hasMethod(clazz, "getConnection"), "Class should have getConnection method");
    }

    @Test
    @Order(7)
    @DisplayName("Test constants are final")
    void testConstantsAreFinal() {
        // Verify the constants are final
        try {
            var urlField = DBConnection.class.getDeclaredField("URL");
            var userField = DBConnection.class.getDeclaredField("USER");
            var passwordField = DBConnection.class.getDeclaredField("PASSWORD");
            
            assertTrue(java.lang.reflect.Modifier.isFinal(urlField.getModifiers()), 
                      "URL should be final");
            assertTrue(java.lang.reflect.Modifier.isFinal(userField.getModifiers()), 
                      "USER should be final");
            assertTrue(java.lang.reflect.Modifier.isFinal(passwordField.getModifiers()), 
                      "PASSWORD should be final");
            
        } catch (Exception e) {
            fail("Failed to access fields: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test constants are static")
    void testConstantsAreStatic() {
        // Verify the constants are static
        try {
            var urlField = DBConnection.class.getDeclaredField("URL");
            var userField = DBConnection.class.getDeclaredField("USER");
            var passwordField = DBConnection.class.getDeclaredField("PASSWORD");
            
            assertTrue(java.lang.reflect.Modifier.isStatic(urlField.getModifiers()), 
                      "URL should be static");
            assertTrue(java.lang.reflect.Modifier.isStatic(userField.getModifiers()), 
                      "USER should be static");
            assertTrue(java.lang.reflect.Modifier.isStatic(passwordField.getModifiers()), 
                      "PASSWORD should be static");
            
        } catch (Exception e) {
            fail("Failed to access fields: " + e.getMessage());
        }
    }

    // Helper methods
    private boolean hasField(Class<?> clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    private boolean hasMethod(Class<?> clazz, String methodName) {
        try {
            clazz.getDeclaredMethod(methodName);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
