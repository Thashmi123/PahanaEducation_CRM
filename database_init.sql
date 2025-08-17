-- Pahana Education Bookshop Database Initialization Script
-- Run this script in MySQL Workbench to set up the database

-- Create Database
CREATE DATABASE IF NOT EXISTS pahanaedu;
USE pahanaedu;

-- Create Tables
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'CASHIER') DEFAULT 'CASHIER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customers (
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    telephone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bills (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number INT NOT NULL,
    user_id INT NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (account_number) REFERENCES customers(account_number) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS bill_items (
    bill_item_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT NOT NULL,
    item_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) GENERATED ALWAYS AS (quantity * unit_price) STORED,
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES items(item_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS audit_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    action VARCHAR(255),
    log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Insert Sample Data

-- Insert default users (password: admin123 for admin, cashier123 for cashier)
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'ADMIN'),
('cashier', 'cashier123', 'CASHIER');

-- Insert sample customers
INSERT INTO customers (name, address, telephone) VALUES 
('John Smith', '123 Main Street, Colombo 01', '+94 11 234 5678'),
('Mary Johnson', '456 Oak Avenue, Colombo 02', '+94 11 345 6789'),
('David Wilson', '789 Pine Road, Colombo 03', '+94 11 456 7890'),
('Sarah Brown', '321 Elm Street, Colombo 04', '+94 11 567 8901'),
('Michael Davis', '654 Maple Drive, Colombo 05', '+94 11 678 9012');

-- Insert sample items
INSERT INTO items (item_name, price, stock_quantity) VALUES 
('Mathematics Textbook Grade 10', 25.99, 50),
('English Literature Book', 18.50, 75),
('Science Lab Manual', 32.75, 30),
('History of Sri Lanka', 22.00, 45),
('Geography Atlas', 28.99, 60),
('Physics Formula Book', 15.50, 80),
('Chemistry Reference Guide', 35.25, 40),
('Biology Study Guide', 27.80, 55),
('Computer Science Basics', 42.99, 25),
('Art and Design Book', 19.75, 70);

-- Insert sample bills
INSERT INTO bills (account_number, user_id, total_amount) VALUES 
(1, 2, 44.49),
(2, 2, 51.25),
(3, 2, 67.99);

-- Insert sample bill items
INSERT INTO bill_items (bill_id, item_id, quantity, unit_price) VALUES 
(1, 1, 1, 25.99),
(1, 2, 1, 18.50),
(2, 3, 1, 32.75),
(2, 4, 1, 18.50),
(3, 5, 1, 28.99),
(3, 6, 1, 15.50),
(3, 7, 1, 23.50);

-- Create indexes for better performance
CREATE INDEX idx_customers_name ON customers(name);
CREATE INDEX idx_items_name ON items(item_name);
CREATE INDEX idx_bills_date ON bills(bill_date);
CREATE INDEX idx_audit_logs_time ON audit_logs(log_time);

-- Show the created data
SELECT 'Users:' as info;
SELECT * FROM users;

SELECT 'Customers:' as info;
SELECT * FROM customers;

SELECT 'Items:' as info;
SELECT * FROM items;

SELECT 'Bills:' as info;
SELECT * FROM bills;

SELECT 'Bill Items:' as info;
SELECT * FROM bill_items;
