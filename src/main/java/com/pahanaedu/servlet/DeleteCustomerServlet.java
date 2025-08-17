package com.pahanaedu.servlet;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteCustomer")
public class DeleteCustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO;
    
    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
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
        
        String accountNumberStr = request.getParameter("accountNumber");
        
        if (accountNumberStr == null || accountNumberStr.trim().isEmpty()) {
            response.sendRedirect("displayCustomer?error=Invalid account number");
            return;
        }
        
        try {
            int accountNumber = Integer.parseInt(accountNumberStr);
            
            // Get customer details for confirmation
            Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);
            
            if (customer == null) {
                response.sendRedirect("displayCustomer?error=Customer not found");
                return;
            }
            
            // Delete the customer
            boolean success = customerDAO.deleteCustomer(accountNumber);
            
            if (success) {
                response.sendRedirect("displayCustomer?success=Customer deleted successfully");
            } else {
                response.sendRedirect("displayCustomer?error=Failed to delete customer");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("displayCustomer?error=Invalid account number format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("displayCustomer?error=An error occurred while deleting customer");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
