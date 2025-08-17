<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.pahanaedu.model.User" %> <% User user = (User)
session.getAttribute("user"); if (user == null) {
response.sendRedirect("login.jsp"); return; } %>
<!DOCTYPE html>
<html>
  <head>
    <title>Pahana Education - Add Customer</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }

      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        background: #f8f9fa;
      }

      .header {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 20px 0;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
        background: rgba(255, 255, 255, 0.2);
      }

      .main-content {
        max-width: 800px;
        margin: 40px auto;
        padding: 0 20px;
      }

      .page-title {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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

      .form-container {
        background: white;
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      }

      .form-group {
        margin-bottom: 25px;
      }

      .form-group label {
        display: block;
        margin-bottom: 8px;
        color: #333;
        font-weight: 500;
        font-size: 16px;
      }

      .form-group input,
      .form-group textarea {
        width: 100%;
        padding: 12px 15px;
        border: 2px solid #e1e5e9;
        border-radius: 5px;
        font-size: 16px;
        transition: border-color 0.3s ease;
      }

      .form-group input:focus,
      .form-group textarea:focus {
        outline: none;
        border-color: #667eea;
      }

      .form-group textarea {
        resize: vertical;
        min-height: 100px;
      }

      .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 20px;
      }

      .submit-btn {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        border: none;
        padding: 15px 30px;
        border-radius: 5px;
        font-size: 16px;
        font-weight: 600;
        cursor: pointer;
        transition: transform 0.2s ease;
        width: 100%;
      }

      .submit-btn:hover {
        transform: translateY(-2px);
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

      @media (max-width: 768px) {
        .form-row {
          grid-template-columns: 1fr;
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
          <a href="displayCustomer">View Customers</a>
          <a href="item">Manage Items</a>
          <a href="help.jsp">Help</a>
          <a href="logout">Logout</a>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="page-title">
        <h2>Add New Customer</h2>
        <p>Register a new customer account in the system</p>
      </div>

      <div class="form-container">
        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message"><%= request.getAttribute("error") %></div>
        <% } %> <% if (request.getAttribute("success") != null) { %>
        <div class="success-message">
          <%= request.getAttribute("success") %>
        </div>
        <% } %>

        <form action="addCustomer" method="post">
          <div class="form-group">
            <label for="name">Customer Name *</label>
            <input
              type="text"
              id="name"
              name="name"
              required
              placeholder="Enter customer's full name"
            />
          </div>

          <div class="form-group">
            <label for="address">Address</label>
            <textarea
              id="address"
              name="address"
              placeholder="Enter customer's address"
            ></textarea>
          </div>

          <div class="form-group">
            <label for="telephone">Telephone Number</label>
            <input
              type="tel"
              id="telephone"
              name="telephone"
              placeholder="Enter phone number"
            />
          </div>

          <button type="submit" class="submit-btn">Add Customer</button>
        </form>
      </div>
    </div>
  </body>
</html>
