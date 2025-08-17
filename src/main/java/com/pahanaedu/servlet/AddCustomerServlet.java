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

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    
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
        
        request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String telephone = request.getParameter("telephone");
            
            // Validate input
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Customer name is required");
                request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
                return;
            }
            
            // Create customer object
            Customer customer = new Customer();
            customer.setName(name.trim());
            customer.setAddress(address != null ? address.trim() : "");
            customer.setTelephone(telephone != null ? telephone.trim() : "");
            
            boolean success = customerDAO.addCustomer(customer);
            
            if (success) {
                request.setAttribute("success", "Customer added successfully!");
                request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Failed to add customer. Please try again.");
                request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error occurred. Please try again.");
            request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
        }
    }
}
