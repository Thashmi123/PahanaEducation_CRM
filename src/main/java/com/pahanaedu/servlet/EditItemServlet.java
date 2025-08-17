package com.pahanaedu.servlet;

import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/editItem")
public class EditItemServlet extends HttpServlet {
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
            
            // Get item details for editing
            Item item = itemDAO.getItemById(itemId);
            
            if (item == null) {
                response.sendRedirect("item?error=Item not found");
                return;
            }
            
            // Set item in request for the JSP
            request.setAttribute("item", item);
            request.getRequestDispatcher("editItem.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("item?error=Invalid item ID format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("item?error=An error occurred while loading item");
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
        
        String itemIdStr = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        String priceStr = request.getParameter("price");
        String stockQuantityStr = request.getParameter("stockQuantity");
        
        // Validate input
        if (itemIdStr == null || itemName == null || priceStr == null || stockQuantityStr == null ||
            itemIdStr.trim().isEmpty() || itemName.trim().isEmpty() || 
            priceStr.trim().isEmpty() || stockQuantityStr.trim().isEmpty()) {
            
            response.sendRedirect("item?error=All fields are required");
            return;
        }
        
        try {
            int itemId = Integer.parseInt(itemIdStr);
            double price = Double.parseDouble(priceStr);
            int stockQuantity = Integer.parseInt(stockQuantityStr);
            
            // Validate values
            if (price < 0 || stockQuantity < 0) {
                response.sendRedirect("item?error=Price and stock quantity must be positive");
                return;
            }
            
            // Create item object
            Item item = new Item();
            item.setItemId(itemId);
            item.setItemName(itemName.trim());
            item.setPrice(BigDecimal.valueOf(price));
            item.setStockQuantity(stockQuantity);
            
            // Update the item
            boolean success = itemDAO.updateItem(item);
            
            if (success) {
                response.sendRedirect("item?success=Item updated successfully");
            } else {
                response.sendRedirect("item?error=Failed to update item");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("item?error=Invalid number format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("item?error=An error occurred while updating item");
        }
    }
}
