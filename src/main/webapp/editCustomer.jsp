<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit Customer - Pahana Education</title>
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
        max-width: 800px;
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
        background: #d8eef5;
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

      .form-container {
        padding: 40px;
      }

      .form-group {
        margin-bottom: 25px;
      }

      .form-group label {
        display: block;
        margin-bottom: 8px;
        font-weight: 600;
        color: #333;
        font-size: 1.1em;
      }

      .form-group input,
      .form-group textarea {
        width: 100%;
        padding: 15px;
        border: 2px solid #e9ecef;
        border-radius: 8px;
        font-size: 1em;
        transition: border-color 0.3s, box-shadow 0.3s;
      }

      .form-group input:focus,
      .form-group textarea:focus {
        outline: none;
        border-color: #667eea;
        box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
      }

      .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 20px;
      }

      .btn {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        padding: 15px 30px;
        border: none;
        border-radius: 8px;
        font-size: 1.1em;
        font-weight: 600;
        cursor: pointer;
        transition: transform 0.2s, box-shadow 0.2s;
        text-decoration: none;
        display: inline-block;
        margin-right: 15px;
      }

      .btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
      }

      .btn-secondary {
        background: #6c757d;
      }

      .btn-secondary:hover {
        box-shadow: 0 10px 20px rgba(108, 117, 125, 0.3);
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

      .form-actions {
        text-align: center;
        margin-top: 30px;
        padding-top: 20px;
        border-top: 1px solid #e9ecef;
      }

      .readonly-field {
        background: #f8f9fa;
        color: #6c757d;
        cursor: not-allowed;
      }
    </style>
  </head>
  <body>
    <div class="container">
      <div class="header">
        <h1>Edit Customer</h1>
        <p>Update customer information in the system</p>
      </div>

      <div class="nav-links">
        <a href="dashboard">Dashboard</a>
        <a href="addCustomer">Add Customer</a>
        <a href="displayCustomer">View Customers</a>
        <a href="item">Manage Items</a>
        <a href="help">Help</a>
        <a href="logout">Logout</a>
      </div>

      <div class="form-container">
        <c:if test="${not empty error}">
          <div class="message error">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
          <div class="message success">${success}</div>
        </c:if>

        <form action="editCustomer" method="post">
          <input
            type="hidden"
            name="accountNumber"
            value="${customer.accountNumber}"
          />

          <div class="form-group">
            <label for="accountNumber">Account Number</label>
            <input
              type="text"
              id="accountNumber"
              value="${customer.accountNumber}"
              class="readonly-field"
              readonly
            />
            <small style="color: #6c757d; font-size: 0.9em"
              >Account number cannot be changed</small
            >
          </div>

          <div class="form-group">
            <label for="name">Customer Name *</label>
            <input
              type="text"
              id="name"
              name="name"
              value="${customer.name}"
              required
            />
          </div>

          <div class="form-group">
            <label for="address">Address</label>
            <textarea id="address" name="address" rows="3">
${customer.address}</textarea
            >
          </div>

          <div class="form-group">
            <label for="telephone">Telephone Number</label>
            <input
              type="tel"
              id="telephone"
              name="telephone"
              value="${customer.telephone}"
            />
          </div>

          <div class="form-actions">
            <button type="submit" class="btn">Update Customer</button>
            <a href="displayCustomer" class="btn btn-secondary">Cancel</a>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
