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
import java.util.List;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    
    private ItemDAO itemDAO;
    
    @Override
    public void init() throws ServletException {
        itemDAO = new ItemDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            if ("edit".equals(action)) {
                String itemIdStr = request.getParameter("itemId");
                if (itemIdStr != null && !itemIdStr.trim().isEmpty()) {
                    int itemId = Integer.parseInt(itemIdStr);
                    Item item = itemDAO.getItemById(itemId);
                    if (item != null) {
                        request.setAttribute("item", item);
                        request.getRequestDispatcher("editItem.jsp").forward(request, response);
                        return;
                    }
                }
                response.sendRedirect("item.jsp");
            } else {
                // Display all items
                String searchTerm = request.getParameter("search");
                List<Item> items;
                
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    items = itemDAO.searchItems(searchTerm.trim());
                } else {
                    items = itemDAO.getAllItems();
                }
                
                request.setAttribute("items", items);
                request.setAttribute("searchTerm", searchTerm);
                request.getRequestDispatcher("item.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error occurred");
            request.getRequestDispatcher("item.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                // Add new item
                String itemName = request.getParameter("itemName");
                String priceStr = request.getParameter("price");
                String stockQuantityStr = request.getParameter("stockQuantity");
                
                if (itemName == null || itemName.trim().isEmpty() || priceStr == null || stockQuantityStr == null) {
                    request.setAttribute("error", "All fields are required");
                    response.sendRedirect("item.jsp");
                    return;
                }
                
                BigDecimal price = new BigDecimal(priceStr);
                int stockQuantity = Integer.parseInt(stockQuantityStr);
                
                Item item = new Item(itemName.trim(), price, stockQuantity);
                boolean success = itemDAO.addItem(item);
                
                if (success) {
                    request.setAttribute("success", "Item added successfully!");
                } else {
                    request.setAttribute("error", "Failed to add item");
                }
                
                response.sendRedirect("item.jsp");
                
            } else if ("update".equals(action)) {
                // Update existing item
                String itemIdStr = request.getParameter("itemId");
                String itemName = request.getParameter("itemName");
                String priceStr = request.getParameter("price");
                String stockQuantityStr = request.getParameter("stockQuantity");
                
                if (itemIdStr == null || itemName == null || itemName.trim().isEmpty() || 
                    priceStr == null || stockQuantityStr == null) {
                    request.setAttribute("error", "All fields are required");
                    response.sendRedirect("item.jsp");
                    return;
                }
                
                int itemId = Integer.parseInt(itemIdStr);
                BigDecimal price = new BigDecimal(priceStr);
                int stockQuantity = Integer.parseInt(stockQuantityStr);
                
                Item item = new Item(itemName.trim(), price, stockQuantity);
                item.setItemId(itemId);
                
                boolean success = itemDAO.updateItem(item);
                
                if (success) {
                    request.setAttribute("success", "Item updated successfully!");
                } else {
                    request.setAttribute("error", "Failed to update item");
                }
                
                response.sendRedirect("item.jsp");
                
            } else if ("delete".equals(action)) {
                // Delete item
                String itemIdStr = request.getParameter("itemId");
                if (itemIdStr != null && !itemIdStr.trim().isEmpty()) {
                    int itemId = Integer.parseInt(itemIdStr);
                    boolean success = itemDAO.deleteItem(itemId);
                    
                    if (success) {
                        request.setAttribute("success", "Item deleted successfully!");
                    } else {
                        request.setAttribute("error", "Failed to delete item");
                    }
                }
                
                response.sendRedirect("item.jsp");
            } else {
                response.sendRedirect("item.jsp");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid numeric value");
            response.sendRedirect("item.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "System error occurred");
            response.sendRedirect("item.jsp");
        }
    }
}
