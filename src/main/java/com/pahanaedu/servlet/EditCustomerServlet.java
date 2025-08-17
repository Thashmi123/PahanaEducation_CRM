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

@WebServlet("/editCustomer")
public class EditCustomerServlet extends HttpServlet {
    
    private CustomerDAO customerDAO;
    
    @Override
    public void init() throws ServletException {
        customerDAO = new CustomerDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String accountNumberStr = request.getParameter("accountNumber");
        if (accountNumberStr == null || accountNumberStr.trim().isEmpty()) {
            response.sendRedirect("displayCustomer.jsp");
            return;
        }
        
        try {
            int accountNumber = Integer.parseInt(accountNumberStr);
            Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);
            
            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Customer not found");
                response.sendRedirect("displayCustomer.jsp");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid account number");
            response.sendRedirect("displayCustomer.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error occurred");
            response.sendRedirect("displayCustomer.jsp");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            String accountNumberStr = request.getParameter("accountNumber");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            
            if (accountNumberStr == null || name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Account number and name are required");
                response.sendRedirect("displayCustomer.jsp");
                return;
            }
            
            int accountNumber = Integer.parseInt(accountNumberStr);
            
            Customer customer = new Customer();
            customer.setAccountNumber(accountNumber);
            customer.setName(name.trim());
            customer.setAddress(address != null ? address.trim() : "");
            customer.setTelephone(telephone != null ? telephone.trim() : "");
            
            boolean success = customerDAO.updateCustomer(customer);
            
            if (success) {
                request.setAttribute("success", "Customer updated successfully!");
            } else {
                request.setAttribute("error", "Failed to update customer");
            }
            
            response.sendRedirect("displayCustomer.jsp");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid account number");
            response.sendRedirect("displayCustomer.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error occurred");
            response.sendRedirect("displayCustomer.jsp");
        }
    }
}
