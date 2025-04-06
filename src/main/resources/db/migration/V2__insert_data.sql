-- Flyway Migration: V2__insert_data.sql

-- Insert Customers
INSERT INTO customers (name, address, phone, email) VALUES
('Acme Corp', '123 Main St, Anytown, CA 91234', '555-123-4567', 'acme@example.com'),
('Global Widgets', '456 Oak Ave, Springfield, IL 62704', '555-987-6543', 'global@example.com'),
('Tech Solutions', '789 Pine Ln, Riverside, NY 12901', '555-111-2222', 'tech@example.com'),
('Retail Direct', '101 Elm Rd, Denver, CO 80202', '555-333-4444', 'retail@example.com'),
('Food Services', '222 Maple Dr, Miami, FL 33101', '555-555-6666', 'food@example.com'),
('Green Energy', '333 Birch St, Seattle, WA 98101', '555-777-8888', 'green@example.com'),
('Book Haven', '444 Cedar Ave, Austin, TX 78701', '555-999-0000', 'book@example.com'),
('Auto Parts', '555 Willow Ln, Detroit, MI 48201', '555-222-3333', 'auto@example.com'),
('Home Decor', '666 Spruce Rd, Chicago, IL 60601', '555-444-5555', 'home@example.com'),
('Pet Supplies', '777 Redwood Dr, Phoenix, AZ 85001', '555-666-7777', 'pet@example.com');

-- Insert Products
INSERT INTO products (name, description, price, stock) VALUES
('Widget A', 'Standard industrial widget', 10.99, 1000),
('Widget B', 'High-performance widget', 24.50, 500),
('Gadget X', 'Portable tech gadget', 49.99, 200),
('Gadget Y', 'Advanced tech gadget', 99.99, 100),
('Organic Apples', 'Fresh organic apples', 2.50, 1500),
('Energy Drink', 'High-caffeine energy drink', 1.99, 2000),
('Novel Book', 'Bestselling fiction novel', 15.00, 300),
('Car Battery', '12V car battery', 89.95, 150),
('Table Lamp', 'Modern design table lamp', 35.00, 400),
('Dog Food', 'Premium dry dog food', 29.99, 600);

-- Insert Orders
INSERT INTO orders (customer_id, order_date, total_amount, status) VALUES
(1, '2023-10-26', 10990.00, 'Shipped'),
(2, '2023-10-27', 12250.00, 'Pending'),
(3, '2023-10-28', 9999.00, 'Completed'),
(4, '2023-10-29', 3500.00, 'Shipped'),
(5, '2023-10-30', 3980.00, 'Pending'),
(6, '2023-10-31', 14000.00, 'Completed'),
(7, '2023-11-01', 4500.00, 'Shipped'),
(8, '2023-11-02', 13492.50, 'Pending'),
(9, '2023-11-03', 14000.00, 'Completed'),
(10, '2023-11-04', 17994.00, 'Shipped');

-- Insert Order Items (Example for Order ID 201 and 202, expand for other orders)
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1000, 10.99), -- Order 201, Widget A x 1000
(2, 2, 500, 24.50); -- Order 202, Widget B x 500

-- Insert Invoices
INSERT INTO invoices (order_id, invoice_date, due_date, amount_paid, payment_status) VALUES
(1, '2023-10-27', '2023-11-26', 10990.00, 'Paid'),
(2, '2023-10-28', '2023-11-27', 0.00, 'Unpaid'),
(3, '2023-10-29', '2023-11-28', 9999.00, 'Paid'),
(4, '2023-10-30', '2023-11-29', 3500.00, 'Paid'),
(5, '2023-10-31', '2023-11-30', 0.00, 'Unpaid'),
(6, '2023-11-01', '2023-12-01', 14000.00, 'Paid'),
(7, '2023-11-02', '2023-12-02', 4500.00, 'Paid'),
(8, '2023-11-03', '2023-12-03', 13492.50, 'Paid'),
(9, '2023-11-04', '2023-12-04', 14000.00, 'Paid'),
(10, '2023-11-05', '2023-12-05', 17994.00, 'Paid');