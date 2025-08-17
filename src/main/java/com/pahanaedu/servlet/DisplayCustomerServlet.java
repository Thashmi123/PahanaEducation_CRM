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
import java.util.List;

@WebServlet("/displayCustomer")
public class DisplayCustomerServlet extends HttpServlet {
    
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
        
        try {
            String searchTerm = request.getParameter("search");
            List<Customer> customers;
            
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                customers = customerDAO.searchCustomers(searchTerm.trim());
            } else {
                customers = customerDAO.getAllCustomers();
            }
            
            request.setAttribute("customers", customers);
            request.setAttribute("searchTerm", searchTerm);
            request.getRequestDispatcher("displayCustomer.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error occurred while retrieving customers");
            request.getRequestDispatcher("displayCustomer.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
