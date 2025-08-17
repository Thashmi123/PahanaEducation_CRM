<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahanaedu.model.User" %>
<%@ page import="com.pahanaedu.model.Item" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Pahana Education - Item Management</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f8f9fa;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 0;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .logo h1 {
            font-size: 24px;
            margin-bottom: 5px;
        }
        
        .logo p {
            font-size: 14px;
            opacity: 0.9;
        }
        
        .nav-links a {
            color: white;
            text-decoration: none;
            margin-left: 20px;
            padding: 8px 16px;
            border-radius: 5px;
            transition: background 0.3s ease;
        }
        
        .nav-links a:hover {
            background: rgba(255,255,255,0.2);
        }
        
        .main-content {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
        }
        
        .page-title {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            text-align: center;
        }
        
        .page-title h2 {
            color: #333;
            margin-bottom: 10px;
            font-size: 28px;
        }
        
        .page-title p {
            color: #666;
            font-size: 16px;
        }
        
        .add-item-section {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        
        .add-item-section h3 {
            color: #333;
            margin-bottom: 20px;
            font-size: 20px;
        }
        
        .add-item-form {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr auto;
            gap: 15px;
            align-items: end;
        }
        
        .form-group {
            display: flex;
            flex-direction: column;
        }
        
        .form-group label {
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }
        
        .form-group input {
            padding: 12px 15px;
            border: 2px solid #e1e5e9;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .add-item-btn {
            background: #51cf66;
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: transform 0.2s ease;
            white-space: nowrap;
        }
        
        .add-item-btn:hover {
            transform: translateY(-2px);
        }
        
        .search-section {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        
        .search-form {
            display: flex;
            gap: 15px;
            align-items: end;
        }
        
        .search-group {
            flex: 1;
        }
        
        .search-group label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        
        .search-group input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e1e5e9;
            border-radius: 5px;
            font-size: 16px;
        }
        
        .search-btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: transform 0.2s ease;
        }
        
        .search-btn:hover {
            transform: translateY(-2px);
        }
        
        .items-table {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .table-header {
            background: #f8f9fa;
            padding: 20px;
            border-bottom: 1px solid #e1e5e9;
        }
        
        .table-header h3 {
            color: #333;
            font-size: 20px;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        th, td {
            padding: 15px 20px;
            text-align: left;
            border-bottom: 1px solid #e1e5e9;
        }
        
        th {
            background: #f8f9fa;
            font-weight: 600;
            color: #333;
        }
        
        tr:hover {
            background: #f8f9fa;
        }
        
        .action-buttons {
            display: flex;
            gap: 10px;
        }
        
        .edit-btn {
            background: #ffd43b;
            color: #333;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            transition: background 0.3s ease;
        }
        
        .edit-btn:hover {
            background: #fcc419;
        }
        
        .delete-btn {
            background: #ff6b6b;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            transition: background 0.3s ease;
        }
        
        .delete-btn:hover {
            background: #fa5252;
        }
        
        .no-items {
            text-align: center;
            padding: 40px;
            color: #666;
        }
        
        .error-message {
            background: #ff6b6b;
            color: white;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 25px;
            text-align: center;
        }
        
        .success-message {
            background: #51cf66;
            color: white;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 25px;
            text-align: center;
        }
        
        .price {
            font-weight: 600;
            color: #51cf66;
        }
        
        .stock {
            font-weight: 600;
        }
        
        .stock.low {
            color: #ff6b6b;
        }
        
        .stock.medium {
            color: #ffd43b;
        }
        
        .stock.high {
            color: #51cf66;
        }
        
        @media (max-width: 768px) {
            .add-item-form {
                grid-template-columns: 1fr;
            }
            
            .search-form {
                flex-direction: column;
            }
            
            .header-content {
                flex-direction: column;
                text-align: center;
            }
            
            .nav-links {
                margin-top: 15px;
            }
            
            .nav-links a {
                margin: 5px;
            }
            
            table {
                font-size: 14px;
            }
            
            th, td {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="header-content">
            <div class="logo">
                <h1>📚 Pahana Education</h1>
                <p>Bookshop Management System</p>
            </div>
            <div class="nav-links">
                <a href="dashboard.jsp">Dashboard</a>
                <a href="addCustomer">Add Customer</a>
                <a href="displayCustomer">View Customers</a>
                <a href="help.jsp">Help</a>
                <a href="logout">Logout</a>
            </div>
        </div>
    </div>
    
    <div class="main-content">
        <div class="page-title">
            <h2>Item Management</h2>
            <p>Add, edit, and manage items in your inventory</p>
        </div>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <% if (request.getAttribute("success") != null) { %>
            <div class="success-message">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>
        
        <div class="add-item-section">
            <h3>Add New Item</h3>
            <form action="item" method="post" class="add-item-form">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="itemName">Item Name</label>
                    <input type="text" id="itemName" name="itemName" required placeholder="Enter item name">
                </div>
                <div class="form-group">
                    <label for="price">Price ($)</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" required placeholder="0.00">
                </div>
                <div class="form-group">
                    <label for="stockQuantity">Stock Quantity</label>
                    <input type="number" id="stockQuantity" name="stockQuantity" min="0" required placeholder="0">
                </div>
                <button type="submit" class="add-item-btn">Add Item</button>
            </form>
        </div>
        
        <div class="search-section">
            <form action="item" method="get" class="search-form">
                <div class="search-group">
                    <label for="search">Search Items</label>
                    <input type="text" id="search" name="search" 
                           value="<%= request.getAttribute("searchTerm") != null ? request.getAttribute("searchTerm") : "" %>"
                           placeholder="Search by item name">
                </div>
                <button type="submit" class="search-btn">Search</button>
            </form>
        </div>
        
        <div class="items-table">
            <div class="table-header">
                <h3>Item List</h3>
            </div>
            
            <% 
            List<Item> items = (List<Item>) request.getAttribute("items");
            if (items != null && !items.isEmpty()) { 
            %>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Item Name</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Created Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Item item : items) { 
                            String stockClass = item.getStockQuantity() <= 5 ? "low" : 
                                              item.getStockQuantity() <= 20 ? "medium" : "high";
                        %>
                            <tr>
                                <td><strong><%= item.getItemId() %></strong></td>
                                <td><%= item.getItemName() %></td>
                                <td class="price">$<%= item.getPrice() %></td>
                                <td class="stock <%= stockClass %>"><%= item.getStockQuantity() %></td>
                                <td><%= item.getCreatedAt() != null ? item.getCreatedAt().toString().substring(0, 19) : "-" %></td>
                                <td class="action-buttons">
                                    <a href="item?action=edit&itemId=<%= item.getItemId() %>" class="edit-btn">Edit</a>
                                    <a href="#" onclick="deleteItem(<%= item.getItemId() %>)" class="delete-btn">Delete</a>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-items">
                    <h3>No items found</h3>
                    <p>Start by adding your first item using the form above.</p>
                </div>
            <% } %>
        </div>
    </div>
    
    <script>
        function deleteItem(itemId) {
            if (confirm('Are you sure you want to delete this item? This action cannot be undone.')) {
                // Create a form to submit the delete action
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = 'item';
                
                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';
                actionInput.value = 'delete';
                
                const itemIdInput = document.createElement('input');
                itemIdInput.type = 'hidden';
                itemIdInput.name = 'itemId';
                itemIdInput.value = itemId;
                
                form.appendChild(actionInput);
                form.appendChild(itemIdInput);
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>
