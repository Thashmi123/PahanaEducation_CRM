<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.pahanaedu.model.User" %> <% User user = (User)
session.getAttribute("user"); if (user == null) {
response.sendRedirect("login.jsp"); return; } %>
<!DOCTYPE html>
<html>
  <head>
    <title>Pahana Education - Dashboard</title>
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

      .user-info {
        text-align: right;
      }

      .user-info p {
        margin-bottom: 5px;
      }

      .logout-btn {
        background: rgba(255, 255, 255, 0.2);
        color: white;
        border: 1px solid rgba(255, 255, 255, 0.3);
        padding: 8px 16px;
        border-radius: 5px;
        text-decoration: none;
        font-size: 14px;
        transition: background 0.3s ease;
      }

      .logout-btn:hover {
        background: rgba(255, 255, 255, 0.3);
      }

      .main-content {
        max-width: 1200px;
        margin: 40px auto;
        padding: 0 20px;
      }

      .welcome-section {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        margin-bottom: 30px;
        text-align: center;
      }

      .welcome-section h2 {
        color: #333;
        margin-bottom: 10px;
        font-size: 28px;
      }

      .welcome-section p {
        color: #666;
        font-size: 16px;
      }

      .menu-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 20px;
        margin-bottom: 30px;
      }

      .menu-card {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        text-align: center;
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        text-decoration: none;
        color: inherit;
      }

      .menu-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 5px 20px rgba(0, 0, 0, 0.15);
      }

      .menu-card .icon {
        font-size: 48px;
        margin-bottom: 20px;
        display: block;
      }

      .menu-card h3 {
        color: #333;
        margin-bottom: 15px;
        font-size: 20px;
      }

      .menu-card p {
        color: #666;
        font-size: 14px;
        line-height: 1.5;
      }

      .stats-section {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      }

      .stats-section h3 {
        color: #333;
        margin-bottom: 20px;
        font-size: 22px;
      }

      .stats-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
      }

      .stat-card {
        text-align: center;
        padding: 20px;
        background: #f8f9fa;
        border-radius: 8px;
      }

      .stat-number {
        font-size: 32px;
        font-weight: bold;
        color: #667eea;
        margin-bottom: 5px;
      }

      .stat-label {
        color: #666;
        font-size: 14px;
      }

      @media (max-width: 768px) {
        .header-content {
          flex-direction: column;
          text-align: center;
        }

        .user-info {
          text-align: center;
          margin-top: 15px;
        }

        .menu-grid {
          grid-template-columns: 1fr;
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
        <div class="user-info">
          <p>Welcome, <strong><%= user.getUsername() %></strong></p>
          <p>Role: <strong><%= user.getRole() %></strong></p>
          <a href="logout" class="logout-btn">Logout</a>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="welcome-section">
        <h2>Welcome to Pahana Education Bookshop</h2>
        <p>
          Manage your customers, items, and billing efficiently with our
          comprehensive system.
        </p>
      </div>

      <div class="menu-grid">
        <a href="addCustomer" class="menu-card">
          <span class="icon">👤</span>
          <h3>Add New Customer</h3>
          <p>
            Register new customers with their account details, contact
            information, and preferences.
          </p>
        </a>

        <a href="displayCustomer" class="menu-card">
          <span class="icon">🔍</span>
          <h3>View Customers</h3>
          <p>
            Browse and search through all customer accounts, edit information,
            and manage records.
          </p>
        </a>

        <a href="item" class="menu-card">
          <span class="icon">📦</span>
          <h3>Manage Items</h3>
          <p>
            Add, update, and delete items in your inventory with pricing and
            stock management.
          </p>
        </a>

        <a href="billing" class="menu-card">
          <span class="icon">🧾</span>
          <h3>Billing System</h3>
          <p>
            Create and manage bills for customers, calculate totals, and
            generate invoices.
          </p>
        </a>

        <a href="help.jsp" class="menu-card">
          <span class="icon">❓</span>
          <h3>Help & Support</h3>
          <p>
            Access system documentation, user guides, and get help with common
            operations.
          </p>
        </a>
      </div>

      <div class="stats-section">
        <h3>Quick Statistics</h3>
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-number">0</div>
            <div class="stat-label">Total Customers</div>
          </div>
          <div class="stat-card">
            <div class="stat-number">0</div>
            <div class="stat-label">Total Items</div>
          </div>
          <div class="stat-card">
            <div class="stat-number">0</div>
            <div class="stat-label">Bills Generated</div>
          </div>
          <div class="stat-card">
            <div class="stat-number">$0.00</div>
            <div class="stat-label">Total Revenue</div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
