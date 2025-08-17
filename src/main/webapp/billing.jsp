<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Billing System - Pahana Education</title>
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
      }

      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        min-height: 100vh;
        padding: 20px;
      }

      .container {
        max-width: 1200px;
        margin: 0 auto;
        background: white;
        border-radius: 15px;
        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
        overflow: hidden;
      }

      .header {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 30px;
        text-align: center;
      }

      .header h1 {
        font-size: 2.5em;
        margin-bottom: 10px;
        font-weight: 300;
      }

      .header p {
        font-size: 1.1em;
        opacity: 0.9;
      }

      .nav-links {
        background: #f8f9fa;
        padding: 15px 30px;
        border-bottom: 1px solid #e9ecef;
      }

      .nav-links a {
        color: #667eea;
        text-decoration: none;
        margin-right: 20px;
        font-weight: 500;
        transition: color 0.3s;
      }

      .nav-links a:hover {
        color: #764ba2;
      }

      .billing-container {
        padding: 30px;
      }

      .billing-section {
        background: #f8f9fa;
        border-radius: 10px;
        padding: 25px;
        margin-bottom: 25px;
      }

      .section-title {
        color: #333;
        font-size: 1.5em;
        margin-bottom: 20px;
        font-weight: 600;
      }

      .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 20px;
        margin-bottom: 20px;
      }

      .form-group {
        margin-bottom: 20px;
      }

      .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: 600;
        color: #333;
      }

      .form-group input,
      .form-group select {
        width: 100%;
        padding: 12px 15px;
        border: 2px solid #e9ecef;
        border-radius: 8px;
        font-size: 1em;
        transition: border-color 0.3s;
      }

      .form-group input:focus,
      .form-group select:focus {
        outline: none;
        border-color: #667eea;
      }

      .btn {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 12px 25px;
        border: none;
        border-radius: 8px;
        font-size: 1em;
        font-weight: 600;
        cursor: pointer;
        transition: transform 0.2s;
        margin-right: 10px;
      }

      .btn:hover {
        transform: translateY(-2px);
      }

      .btn-success {
        background: #28a745;
      }

      .btn-warning {
        background: #ffc107;
        color: #333;
      }

      .btn-danger {
        background: #dc3545;
      }

      .btn-print {
        background: #17a2b8;
      }

      .items-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background: white;
        border-radius: 8px;
        overflow: hidden;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      }

      .items-table th,
      .items-table td {
        padding: 15px;
        text-align: left;
        border-bottom: 1px solid #e9ecef;
      }

      .items-table th {
        background: #f8f9fa;
        font-weight: 600;
        color: #333;
      }

      .items-table tr:hover {
        background: #f8f9fa;
      }

      .quantity-input {
        width: 80px;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
        text-align: center;
      }

      .total-section {
        background: #e8f5e8;
        border: 2px solid #28a745;
        border-radius: 10px;
        padding: 25px;
        margin-top: 25px;
        text-align: right;
      }

      .total-amount {
        font-size: 2em;
        font-weight: 700;
        color: #28a745;
        margin-bottom: 10px;
      }

      .total-label {
        font-size: 1.2em;
        color: #333;
        font-weight: 600;
      }

      .message {
        padding: 15px;
        border-radius: 8px;
        margin-bottom: 20px;
        font-weight: 500;
      }

      .message.error {
        background: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
      }

      .message.success {
        background: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
      }

      .no-items {
        text-align: center;
        padding: 40px;
        color: #666;
        font-style: italic;
      }

      .print-section {
        text-align: center;
        margin-top: 30px;
        padding-top: 20px;
        border-top: 2px solid #e9ecef;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <h1>🧾 Billing System</h1>
        <p>Generate bills and invoices for customers</p>
      </div>

      <div class="nav-links">
        <a href="dashboard.jsp">Dashboard</a>
        <a href="addCustomer">Add Customer</a>
        <a href="displayCustomer">View Customers</a>
        <a href="item">Manage Items</a>
        <a href="help.jsp">Help</a>
        <a href="logout">Logout</a>
      </div>

      <div class="billing-container">
        <c:if test="${not empty error}">
          <div class="message error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
          <div class="message success">${success}</div>
        </c:if>

        <!-- Customer Selection Section -->
        <div class="billing-section">
          <h3 class="section-title">👤 Select Customer</h3>
          <form action="billing" method="get" id="customerForm">
            <div class="form-row">
              <div class="form-group">
                <label for="customerSelect">Customer Account Number</label>
                <select
                  id="customerSelect"
                  name="accountNumber"
                  required
                  onchange="loadCustomerDetails()"
                >
                  <option value="">-- Select Customer --</option>
                  <c:forEach var="customer" items="${customers}">
                    <option value="${customer.accountNumber}">
                      ${customer.accountNumber} - ${customer.name}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="form-group">
                <label>Customer Name</label>
                <input
                  type="text"
                  id="customerName"
                  readonly
                  style="background: #f8f9fa"
                />
              </div>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>Address</label>
                <input
                  type="text"
                  id="customerAddress"
                  readonly
                  style="background: #f8f9fa"
                />
              </div>
              <div class="form-group">
                <label>Telephone</label>
                <input
                  type="text"
                  id="customerTelephone"
                  readonly
                  style="background: #f8f9fa"
                />
              </div>
            </div>
          </form>
        </div>

        <!-- Item Selection Section -->
        <div class="billing-section">
          <h3 class="section-title">📦 Add Items to Bill</h3>
          <form action="billing" method="post" id="itemForm">
            <input type="hidden" name="action" value="addItem" />
            <input
              type="hidden"
              name="accountNumber"
              id="hiddenAccountNumber"
            />

            <div class="form-row">
              <div class="form-group">
                <label for="itemSelect">Select Item</label>
                <select id="itemSelect" name="itemId" required>
                  <option value="">-- Select Item --</option>
                  <c:forEach var="item" items="${items}">
                    <option
                      value="${item.itemId}"
                      data-price="${item.price}"
                      data-stock="${item.stockQuantity}"
                    >
                      ${item.itemName} - Rs. ${item.price} (Stock:
                      ${item.stockQuantity})
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="form-group">
                <label for="quantity">Quantity</label>
                <input
                  type="number"
                  id="quantity"
                  name="quantity"
                  min="1"
                  value="1"
                  required
                  onchange="updateSubtotal()"
                />
              </div>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label>Unit Price (Rs.)</label>
                <input
                  type="text"
                  id="unitPrice"
                  readonly
                  style="background: #f8f9fa"
                />
              </div>
              <div class="form-group">
                <label>Subtotal (Rs.)</label>
                <input
                  type="text"
                  id="subtotal"
                  readonly
                  style="background: #f8f9fa"
                />
              </div>
            </div>

            <button type="submit" class="btn btn-success">
              ➕ Add Item to Bill
            </button>
          </form>
        </div>

        <!-- Bill Items Table -->
        <div class="billing-section">
          <h3 class="section-title">📋 Bill Items</h3>
          <c:if test="${not empty billItems}">
            <table class="items-table">
              <thead>
                <tr>
                  <th>Item Name</th>
                  <th>Quantity</th>
                  <th>Unit Price (Rs.)</th>
                  <th>Subtotal (Rs.)</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="billItem" items="${billItems}">
                  <tr>
                    <td>${billItem.itemName}</td>
                    <td>${billItem.quantity}</td>
                    <td>${billItem.unitPrice}</td>
                    <td>${billItem.subtotal}</td>
                    <td>
                      <button
                        onclick="removeItem(${billItem.billItemId})"
                        class="btn btn-danger"
                        style="padding: 8px 12px; font-size: 0.9em"
                      >
                        ❌ Remove
                      </button>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>

            <!-- Total Section -->
            <div class="total-section">
              <div class="total-label">Total Bill Amount:</div>
              <div class="total-amount">Rs. ${totalAmount}</div>
            </div>

            <!-- Bill Actions -->
            <div class="print-section">
              <button onclick="generateBill()" class="btn btn-success">
                💾 Save Bill
              </button>
              <button onclick="printBill()" class="btn btn-print">
                🖨️ Print Bill
              </button>
              <button onclick="clearBill()" class="btn btn-warning">
                🗑️ Clear Bill
              </button>
            </div>
          </c:if>

          <c:if test="${empty billItems}">
            <div class="no-items">
              <h3>No items in bill</h3>
              <p>Select a customer and add items to start generating a bill.</p>
            </div>
          </c:if>
        </div>
      </div>
    </div>

    <script>
      // Load customer details when customer is selected
      function loadCustomerDetails() {
        const customerSelect = document.getElementById("customerSelect");
        const selectedOption =
          customerSelect.options[customerSelect.selectedIndex];
        const hiddenAccountNumber = document.getElementById(
          "hiddenAccountNumber"
        );

        if (customerSelect.value) {
          hiddenAccountNumber.value = customerSelect.value;

          // Extract customer details from option text
          const customerText = selectedOption.text;
          const parts = customerText.split(" - ");

          if (parts.length >= 2) {
            document.getElementById("customerName").value = parts[1];
            // You can add AJAX call here to get full customer details
          }
        }
      }

      // Update subtotal when quantity changes
      function updateSubtotal() {
        const itemSelect = document.getElementById("itemSelect");
        const quantity = document.getElementById("quantity").value;
        const selectedOption = itemSelect.options[itemSelect.selectedIndex];

        if (selectedOption.value && quantity) {
          const price = parseFloat(selectedOption.getAttribute("data-price"));
          const subtotal = price * parseInt(quantity);

          document.getElementById("unitPrice").value = price.toFixed(2);
          document.getElementById("subtotal").value = subtotal.toFixed(2);
        }
      }

      // Update subtotal when item changes
      document
        .getElementById("itemSelect")
        .addEventListener("change", function () {
          updateSubtotal();
        });

      // Remove item from bill
      function removeItem(billItemId) {
        if (
          confirm("Are you sure you want to remove this item from the bill?")
        ) {
          window.location.href =
            "billing?action=removeItem&billItemId=" + billItemId;
        }
      }

      // Generate and save bill
      function generateBill() {
        if (
          confirm(
            "Are you sure you want to save this bill? This action cannot be undone."
          )
        ) {
          window.location.href = "billing?action=saveBill";
        }
      }

      // Print bill
      function printBill() {
        window.print();
      }

      // Clear bill
      function clearBill() {
        if (
          confirm(
            "Are you sure you want to clear this bill? All items will be removed."
          )
        ) {
          window.location.href = "billing?action=clearBill";
        }
      }
    </script>
  </body>
</html>
