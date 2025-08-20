package com.pahanaedu.servlet;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BillingServletTest {

    private BillingServlet billingServlet;
    
    @Mock
    private HttpServletRequest mockRequest;
    
    @Mock
    private HttpServletResponse mockResponse;
    
    @Mock
    private HttpSession mockSession;
    
    @Mock
    private RequestDispatcher mockRequestDispatcher;
    
    @Mock
    private CustomerDAO mockCustomerDAO;
    
    @Mock
    private ItemDAO mockItemDAO;
    
    @Mock
    private BillDAO mockBillDAO;

    @BeforeEach
    void setUp() {
        billingServlet = new BillingServlet();
        
        // Mock session
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockRequest.getSession()).thenReturn(mockSession);
        
        // Mock request dispatcher
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
    }

    @Test
    @Order(1)
    @DisplayName("Test display billing page with valid session")
    void testDisplayBillingPage_ValidSession() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        
        List<Customer> mockCustomers = createMockCustomers();
        List<Item> mockItems = createMockItems();
        List<BillItem> mockBillItems = new ArrayList<>();
        
        when(mockCustomerDAO.getAllCustomers()).thenReturn(mockCustomers);
        when(mockItemDAO.getAllItems()).thenReturn(mockItems);
        when(mockSession.getAttribute("billItems")).thenReturn(mockBillItems);
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockRequest).setAttribute("customers", mockCustomers);
        verify(mockRequest).setAttribute("items", mockItems);
        verify(mockRequest).setAttribute("billItems", mockBillItems);
        verify(mockRequest).setAttribute("totalAmount", BigDecimal.ZERO);
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    @Order(2)
    @DisplayName("Test display billing page without session - redirect to login")
    void testDisplayBillingPage_NoSession() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(null);
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("login");
        verify(mockRequestDispatcher, never()).forward(any(), any());
    }

    @Test
    @Order(3)
    @DisplayName("Test add item to bill successfully")
    void testAddItemToBill_Success() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("addItem");
        when(mockRequest.getParameter("accountNumber")).thenReturn("1");
        when(mockRequest.getParameter("itemId")).thenReturn("1");
        when(mockRequest.getParameter("quantity")).thenReturn("2");
        
        Item mockItem = createMockItem();
        when(mockItemDAO.getItemById(1)).thenReturn(mockItem);
        
        List<BillItem> mockBillItems = new ArrayList<>();
        when(mockSession.getAttribute("billItems")).thenReturn(mockBillItems);
        
        // Act
        billingServlet.doPost(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("billing?success=Item added to bill successfully");
        verify(mockSession).setAttribute("billItems", any(List.class));
    }

    @Test
    @Order(4)
    @DisplayName("Test add item to bill with insufficient stock")
    void testAddItemToBill_InsufficientStock() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("addItem");
        when(mockRequest.getParameter("accountNumber")).thenReturn("1");
        when(mockRequest.getParameter("itemId")).thenReturn("1");
        when(mockRequest.getParameter("quantity")).thenReturn("100"); // More than available stock
        
        Item mockItem = createMockItem();
        when(mockItemDAO.getItemById(1)).thenReturn(mockItem);
        
        // Act
        billingServlet.doPost(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("billing?error=Insufficient stock. Available: 50");
    }

    @Test
    @Order(5)
    @DisplayName("Test add item to bill with missing parameters")
    void testAddItemToBill_MissingParameters() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("addItem");
        when(mockRequest.getParameter("accountNumber")).thenReturn("");
        when(mockRequest.getParameter("itemId")).thenReturn("");
        when(mockRequest.getParameter("quantity")).thenReturn("");
        
        // Act
        billingServlet.doPost(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("billing?error=All fields are required");
    }

    @Test
    @Order(6)
    @DisplayName("Test remove item from bill successfully")
    void testRemoveItemFromBill_Success() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("removeItem");
        when(mockRequest.getParameter("billItemId")).thenReturn("1");
        
        List<BillItem> mockBillItems = createMockBillItems();
        when(mockSession.getAttribute("billItems")).thenReturn(mockBillItems);
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("billing?success=Item removed from bill successfully");
    }

    @Test
    @Order(7)
    @DisplayName("Test save bill successfully")
    void testSaveBill_Success() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("saveBill");
        when(mockRequest.getParameter("accountNumber")).thenReturn("1");
        
        List<BillItem> mockBillItems = createMockBillItems();
        when(mockSession.getAttribute("billItems")).thenReturn(mockBillItems);
        
        when(mockBillDAO.saveBill(any(Bill.class))).thenReturn(true);
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockBillDAO).saveBill(any(Bill.class));
        verify(mockSession).removeAttribute("billItems");
        verify(mockResponse).sendRedirect("billing?success=Bill saved successfully");
    }

    @Test
    @Order(8)
    @DisplayName("Test save bill with no items")
    void testSaveBill_NoItems() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("saveBill");
        when(mockRequest.getParameter("accountNumber")).thenReturn("1");
        
        when(mockSession.getAttribute("billItems")).thenReturn(new ArrayList<>());
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("billing?error=No items in bill to save");
    }

    @Test
    @Order(9)
    @DisplayName("Test save bill failure")
    void testSaveBill_Failure() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("saveBill");
        when(mockRequest.getParameter("accountNumber")).thenReturn("1");
        
        List<BillItem> mockBillItems = createMockBillItems();
        when(mockSession.getAttribute("billItems")).thenReturn(mockBillItems);
        
        when(mockBillDAO.saveBill(any(Bill.class))).thenReturn(false);
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("billing?error=Failed to save bill");
    }

    @Test
    @Order(10)
    @DisplayName("Test clear bill successfully")
    void testClearBill_Success() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("clearBill");
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockSession).removeAttribute("billItems");
        verify(mockResponse).sendRedirect("billing?success=Bill cleared successfully");
    }

    @Test
    @Order(11)
    @DisplayName("Test invalid action parameter")
    void testInvalidAction() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockRequest.getParameter("action")).thenReturn("invalidAction");
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    @Order(12)
    @DisplayName("Test exception handling in display billing page")
    void testExceptionHandling_DisplayBillingPage() throws Exception {
        // Arrange
        when(mockSession.getAttribute("user")).thenReturn(createMockUser());
        when(mockCustomerDAO.getAllCustomers()).thenThrow(new RuntimeException("Database error"));
        
        // Act
        billingServlet.doGet(mockRequest, mockResponse);
        
        // Assert
        verify(mockResponse).sendRedirect("billing?error=Failed to load billing page");
    }

    // Helper methods to create mock objects
    private User createMockUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("testuser");
        user.setRole("CASHIER");
        return user;
    }

    private List<Customer> createMockCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setAccountNumber(1);
        customer.setName("John Doe");
        customer.setAddress("123 Test Street");
        customer.setTelephone("+94 11 123 4567");
        customers.add(customer);
        return customers;
    }

    private List<Item> createMockItems() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("Test Book");
        item.setPrice(new BigDecimal("25.99"));
        item.setStockQuantity(50);
        items.add(item);
        return items;
    }

    private Item createMockItem() {
        Item item = new Item();
        item.setItemId(1);
        item.setItemName("Test Book");
        item.setPrice(new BigDecimal("25.99"));
        item.setStockQuantity(50);
        return item;
    }

    private List<BillItem> createMockBillItems() {
        List<BillItem> billItems = new ArrayList<>();
        BillItem billItem = new BillItem();
        billItem.setBillItemId(1);
        billItem.setItemId(1);
        billItem.setItemName("Test Book");
        billItem.setQuantity(2);
        billItem.setUnitPrice(new BigDecimal("25.99"));
        billItem.setSubtotal(new BigDecimal("51.98"));
        billItems.add(billItem);
        return billItems;
    }
}
