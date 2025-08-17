<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ page
import="com.pahanaedu.model.User" %> <% User user = (User)
session.getAttribute("user"); if (user == null) {
response.sendRedirect("login.jsp"); return; } %>
<!DOCTYPE html>
<html>
  <head>
    <title>Pahana Education - Help & Support</title>
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
        max-width: 1000px;
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

      .help-section {
        background: white;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        margin-bottom: 30px;
      }

      .help-section h3 {
        color: #333;
        margin-bottom: 20px;
        font-size: 22px;
        border-bottom: 2px solid #667eea;
        padding-bottom: 10px;
      }

      .help-section p {
        color: #666;
        line-height: 1.6;
        margin-bottom: 15px;
      }

      .help-section ul {
        margin-left: 20px;
        margin-bottom: 15px;
      }

      .help-section li {
        color: #666;
        line-height: 1.6;
        margin-bottom: 8px;
      }

      .feature-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: 20px;
        margin: 20px 0;
      }

      .feature-card {
        background: #f8f9fa;
        padding: 20px;
        border-radius: 8px;
        border-left: 4px solid #667eea;
      }

      .feature-card h4 {
        color: #333;
        margin-bottom: 10px;
        font-size: 18px;
      }

      .feature-card p {
        color: #666;
        font-size: 14px;
        line-height: 1.5;
      }

      .contact-info {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 30px;
        border-radius: 10px;
        text-align: center;
      }

      .contact-info h3 {
        margin-bottom: 15px;
        font-size: 22px;
      }

      .contact-info p {
        margin-bottom: 10px;
        opacity: 0.9;
      }

      .back-to-top {
        position: fixed;
        bottom: 30px;
        right: 30px;
        background: #667eea;
        color: white;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        text-decoration: none;
        font-size: 20px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
        transition: transform 0.3s ease;
      }

      .back-to-top:hover {
        transform: translateY(-3px);
      }

      @media (max-width: 768px) {
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

        .feature-grid {
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
        <div class="nav-links">
          <a href="dashboard.jsp">Dashboard</a>
          <a href="addCustomer">Add Customer</a>
          <a href="displayCustomer">View Customers</a>
          <a href="item">Manage Items</a>
          <a href="logout">Logout</a>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="page-title">
        <h2>Help & Support</h2>
        <p>
          Complete guide to using the Pahana Education Bookshop Management
          System
        </p>
      </div>

      <div class="help-section">
        <h3>🚀 Getting Started</h3>
        <p>
          Welcome to the Pahana Education Bookshop Management System! This
          comprehensive platform helps you manage your bookshop operations
          efficiently. Here's how to get started:
        </p>

        <div class="feature-grid">
          <div class="feature-card">
            <h4>1. Login</h4>
            <p>
              Use your assigned username and password to access the system.
              Contact your administrator if you need login credentials.
            </p>
          </div>
          <div class="feature-card">
            <h4>2. Dashboard</h4>
            <p>
              Start from the dashboard to access all major functions. The
              dashboard provides quick navigation to all system features.
            </p>
          </div>
          <div class="feature-card">
            <h4>3. Navigation</h4>
            <p>
              Use the navigation menu at the top of each page to move between
              different sections of the system.
            </p>
          </div>
        </div>
      </div>

      <div class="help-section">
        <h3>👥 Customer Management</h3>
        <p>
          The customer management module allows you to maintain comprehensive
          customer records:
        </p>

        <h4>Adding New Customers:</h4>
        <ul>
          <li>Navigate to "Add New Customer" from the dashboard</li>
          <li>Fill in the customer's name (required)</li>
          <li>Add address and telephone number (optional)</li>
          <li>Click "Add Customer" to save</li>
        </ul>

        <h4>Viewing and Managing Customers:</h4>
        <ul>
          <li>Use "View Customers" to see all customer records</li>
          <li>Search customers by name, address, or telephone</li>
          <li>Edit customer information using the "Edit" button</li>
          <li>Delete customers when necessary (use with caution)</li>
        </ul>
      </div>

      <div class="help-section">
        <h3>📦 Item Management</h3>
        <p>Manage your bookshop inventory efficiently:</p>

        <h4>Adding Items:</h4>
        <ul>
          <li>Go to "Manage Items" from the dashboard</li>
          <li>Enter item name, price, and stock quantity</li>
          <li>Click "Add Item" to save to inventory</li>
        </ul>

        <h4>Managing Inventory:</h4>
        <ul>
          <li>View all items in a comprehensive table</li>
          <li>Search items by name</li>
          <li>Edit item details including price and stock</li>
          <li>
            Monitor stock levels (color-coded: red for low, yellow for medium,
            green for high)
          </li>
        </ul>
      </div>

      <div class="help-section">
        <h3>🧾 Billing System</h3>
        <p>The billing system helps you create and manage customer invoices:</p>

        <h4>Creating Bills:</h4>
        <ul>
          <li>Select a customer from your customer list</li>
          <li>Add items to the bill with quantities</li>
          <li>System automatically calculates totals</li>
          <li>Generate and print invoices</li>
        </ul>

        <h4>Bill Management:</h4>
        <ul>
          <li>View bill history and details</li>
          <li>Track payment status</li>
          <li>Generate reports for accounting</li>
        </ul>
      </div>

      <div class="help-section">
        <h3>🔒 Security Features</h3>
        <p>Your system includes several security measures:</p>

        <ul>
          <li>
            <strong>User Authentication:</strong> Secure login with username and
            password
          </li>
          <li>
            <strong>Session Management:</strong> Automatic logout after 30
            minutes of inactivity
          </li>
          <li>
            <strong>Role-Based Access:</strong> Different permissions for
            different user types
          </li>
          <li>
            <strong>Audit Logging:</strong> Track all system activities for
            security
          </li>
        </ul>
      </div>

      <div class="help-section">
        <h3>💡 Tips for Best Performance</h3>
        <ul>
          <li>
            Regularly update customer information to maintain accurate records
          </li>
          <li>Monitor stock levels to avoid running out of popular items</li>
          <li>Use the search function to quickly find customers or items</li>
          <li>Log out when finished to maintain security</li>
          <li>Keep your browser updated for optimal performance</li>
        </ul>
      </div>

      <div class="help-section">
        <h3>❓ Troubleshooting</h3>

        <h4>Common Issues:</h4>
        <ul>
          <li>
            <strong>Can't Login:</strong> Check your username and password.
            Contact administrator if issues persist.
          </li>
          <li>
            <strong>Page Not Loading:</strong> Refresh the page or try logging
            out and back in.
          </li>
          <li>
            <strong>Data Not Saving:</strong> Ensure all required fields are
            filled and try again.
          </li>
          <li>
            <strong>Slow Performance:</strong> Close other browser tabs and
            clear browser cache.
          </li>
        </ul>

        <h4>Browser Compatibility:</h4>
        <p>
          This system works best with modern browsers like Chrome, Firefox,
          Safari, and Edge. Ensure your browser is updated to the latest
          version.
        </p>
      </div>

      <div class="contact-info">
        <h3>📞 Need More Help?</h3>
        <p>If you need additional assistance or encounter technical issues:</p>
        <p>
          <strong>Contact System Administrator:</strong> admin@pahanaedu.com
        </p>
        <p><strong>Technical Support:</strong> support@pahanaedu.com</p>
        <p><strong>Phone:</strong> +94 11 234 5678</p>
      </div>
    </div>

    <a
      href="#"
      class="back-to-top"
      onclick="window.scrollTo({top: 0, behavior: 'smooth'})"
      >↑</a
    >
  </body>
</html>
