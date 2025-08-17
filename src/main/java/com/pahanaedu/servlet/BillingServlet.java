package com.pahanaedu.servlet;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Bill;
import com.pahanaedu.model.BillItem;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Item;
import com.pahanaedu.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    private BillDAO billDAO;
    
    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
        itemDAO = new ItemDAO();
        billDAO = new BillDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("removeItem".equals(action)) {
            removeItemFromBill(request, response, session);
        } else if ("saveBill".equals(action)) {
            saveBill(request, response, session);
        } else if ("clearBill".equals(action)) {
            clearBill(request, response, session);
        } else {
            // Display billing page
            displayBillingPage(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("addItem".equals(action)) {
            addItemToBill(request, response, session);
        } else {
            response.sendRedirect("billing");
        }
    }
    
    private void displayBillingPage(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get all customers and items for the form
            List<Customer> customers = customerDAO.getAllCustomers();
            List<Item> items = itemDAO.getAllItems();
            
            // Get current bill items from session
            HttpSession session = request.getSession();
            @SuppressWarnings("unchecked")
            List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
            
            if (billItems == null) {
                billItems = new ArrayList<>();
                session.setAttribute("billItems", billItems);
            }
            
            // Calculate total amount
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (BillItem item : billItems) {
                totalAmount = totalAmount.add(item.getSubtotal());
            }
            
            request.setAttribute("customers", customers);
            request.setAttribute("items", items);
            request.setAttribute("billItems", billItems);
            request.setAttribute("totalAmount", totalAmount);
            
            request.getRequestDispatcher("billing.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while loading the billing page");
            response.sendRedirect("billing?error=Failed to load billing page");
        }
    }
    
    private void addItemToBill(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        
        try {
            String accountNumberStr = request.getParameter("accountNumber");
            String itemIdStr = request.getParameter("itemId");
            String quantityStr = request.getParameter("quantity");
            
            // Validate input
            if (accountNumberStr == null || itemIdStr == null || quantityStr == null ||
                accountNumberStr.trim().isEmpty() || itemIdStr.trim().isEmpty() || quantityStr.trim().isEmpty()) {
                
                response.sendRedirect("billing?error=All fields are required");
                return;
            }
            
            int accountNumber = Integer.parseInt(accountNumberStr);
            int itemId = Integer.parseInt(itemIdStr);
            int quantity = Integer.parseInt(quantityStr);
            
            // Validate values
            if (quantity <= 0) {
                response.sendRedirect("billing?error=Quantity must be positive");
                return;
            }
            
            // Get item details
            Item item = itemDAO.getItemById(itemId);
            if (item == null) {
                response.sendRedirect("billing?error=Item not found");
                return;
            }
            
            // Check stock availability
            if (item.getStockQuantity() < quantity) {
                response.sendRedirect("billing?error=Insufficient stock. Available: " + item.getStockQuantity());
                return;
            }
            
            // Create bill item
            BillItem billItem = new BillItem();
            billItem.setItemId(itemId);
            billItem.setItemName(item.getItemName());
            billItem.setQuantity(quantity);
            billItem.setUnitPrice(item.getPrice());
            billItem.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
            
            // Add to session
            @SuppressWarnings("unchecked")
            List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
            if (billItems == null) {
                billItems = new ArrayList<>();
                session.setAttribute("billItems", billItems);
            }
            
            billItems.add(billItem);
            
            response.sendRedirect("billing?success=Item added to bill successfully");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("billing?error=Invalid number format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("billing?error=An error occurred while adding item to bill");
        }
    }
    
    private void removeItemFromBill(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        
        try {
            String billItemIdStr = request.getParameter("billItemId");
            
            if (billItemIdStr == null || billItemIdStr.trim().isEmpty()) {
                response.sendRedirect("billing?error=Invalid item ID");
                return;
            }
            
            int billItemId = Integer.parseInt(billItemIdStr);
            
            @SuppressWarnings("unchecked")
            List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
            
            if (billItems != null) {
                billItems.removeIf(item -> item.getBillItemId() == billItemId);
                session.setAttribute("billItems", billItems);
                response.sendRedirect("billing?success=Item removed from bill successfully");
            } else {
                response.sendRedirect("billing?error=No items in bill");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("billing?error=Invalid item ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("billing?error=An error occurred while removing item");
        }
    }
    
    private void saveBill(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        
        try {
            @SuppressWarnings("unchecked")
            List<BillItem> billItems = (List<BillItem>) session.getAttribute("billItems");
            
            if (billItems == null || billItems.isEmpty()) {
                response.sendRedirect("billing?error=No items in bill to save");
                return;
            }
            
            // Get customer account number from first item (you might want to store this separately)
            String accountNumberStr = request.getParameter("accountNumber");
            if (accountNumberStr == null || accountNumberStr.trim().isEmpty()) {
                response.sendRedirect("billing?error=Please select a customer");
                return;
            }
            
            int accountNumber = Integer.parseInt(accountNumberStr);
            
            // Get current user
            User user = (User) session.getAttribute("user");
            
            // Calculate total amount
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (BillItem item : billItems) {
                totalAmount = totalAmount.add(item.getSubtotal());
            }
            
            // Create bill
            Bill bill = new Bill();
            bill.setAccountNumber(accountNumber);
            bill.setUserId(user.getUserId());
            bill.setTotalAmount(totalAmount);
            bill.setBillItems(billItems);
            
            // Save bill to database
            boolean success = billDAO.saveBill(bill);
            
            if (success) {
                // Clear bill items from session
                session.removeAttribute("billItems");
                response.sendRedirect("billing?success=Bill saved successfully");
            } else {
                response.sendRedirect("billing?error=Failed to save bill");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("billing?error=Invalid account number format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("billing?error=An error occurred while saving bill");
        }
    }
    
    private void clearBill(HttpServletRequest request, HttpServletResponse response, HttpSession session) 
            throws ServletException, IOException {
        
        try {
            // Clear bill items from session
            session.removeAttribute("billItems");
            response.sendRedirect("billing?success=Bill cleared successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("billing?error=An error occurred while clearing bill");
        }
    }
}
