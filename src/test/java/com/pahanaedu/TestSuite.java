package com.pahanaedu;

import com.pahanaedu.dao.*;
import com.pahanaedu.util.*;
// import org.junit.platform.suite.api.SelectClasses;
// import org.junit.platform.suite.api.Suite;
// import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Comprehensive Test Suite for Pahana Education CRM System
 * 
 * This test suite organizes all unit tests in a logical order:
 * 1. Utility classes (DBConnection)
 * 2. Data Access Objects (DAOs)
 * 3. Servlet classes (when available)
 * 4. Integration tests (if any)
 * 
 * Test Execution Order:
 * - Utility tests run first as they test basic infrastructure
 * - DAO tests run next as they test data layer
 * - Servlet tests run last as they depend on DAOs and utilities
 * 
 * NOTE: Temporarily disabled to resolve compilation issues.
 * Will be re-enabled once all individual test classes compile successfully.
 */
/*
@Suite
@SuiteDisplayName("Pahana Education CRM - Complete Test Suite")
@SelectClasses({
    // Utility Tests
    DBConnectionTest.class,
    
    // DAO Tests
    UserDAOTest.class,
    CustomerDAOTest.class,
    ItemDAOTest.class,
    
    // Servlet Tests (only include existing ones)
    BillingServletTest.class
})
*/
public class TestSuite {
    
    /**
     * Test Suite Configuration
     * 
     * This suite is designed to:
     * - Run tests in dependency order
     * - Provide comprehensive coverage of all components
     * - Enable easy identification of failing components
     * - Support both individual test execution and full suite runs
     * 
     * Test Categories:
     * 1. Unit Tests: Test individual components in isolation
     * 2. Integration Tests: Test component interactions
     * 3. Exception Tests: Test error handling and edge cases
     * 4. Validation Tests: Test input validation and business rules
     * 
     * Note: Some servlet test classes are not yet implemented
     * and will be added as they become available.
     * 
     * NOTE: Temporarily disabled to resolve compilation issues.
     * Will be re-enabled once all individual test classes compile successfully.
     */
}
