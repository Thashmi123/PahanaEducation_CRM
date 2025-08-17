package com.pahanaedu.servlet;

import com.pahanaedu.dao.ItemDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {
    private ItemDAO itemDAO;
    
    @Override
    public void init() throws ServletException {
        itemDAO = new ItemDAO();
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
        
        String itemIdStr = request.getParameter("itemId");
        
        if (itemIdStr == null || itemIdStr.trim().isEmpty()) {
            response.sendRedirect("item?error=Invalid item ID");
            return;
        }
        
        try {
            int itemId = Integer.parseInt(itemIdStr);
            
            // Delete the item
            boolean success = itemDAO.deleteItem(itemId);
            
            if (success) {
                response.sendRedirect("item?success=Item deleted successfully");
            } else {
                response.sendRedirect("item?error=Failed to delete item");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("item?error=Invalid item ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("item?error=An error occurred while deleting item");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
