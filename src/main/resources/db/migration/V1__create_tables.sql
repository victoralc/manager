-- Flyway Migration: V1__create_tables.sql

-- Customers Table
CREATE TABLE customers
(
    customer_id BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    address     VARCHAR(255),
    phone       VARCHAR(20),
    email       VARCHAR(255) UNIQUE
);

-- Products Table
CREATE TABLE products
(
    product_id  BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    stock       INTEGER        NOT NULL
);

CREATE TABLE order_delivery
(
    id          BIGSERIAL PRIMARY KEY,
    street      VARCHAR(255),
    number      VARCHAR(50),
    postal_code VARCHAR(20),
    complement  VARCHAR(255),
    city        VARCHAR(100),
    country     VARCHAR(100),
    status      VARCHAR(50) NOT NULL
);

-- Orders Table
CREATE TABLE orders
(
    order_id          BIGSERIAL PRIMARY KEY,
    customer_id       INTEGER REFERENCES customers (customer_id),
    order_delivery_id INTEGER REFERENCES order_delivery (id),
    order_date        DATE           NOT NULL,
    total_amount      DECIMAL(10, 2) NOT NULL,
    status            VARCHAR(50)    NOT NULL
);

-- Order Items Table
CREATE TABLE order_items
(
    order_item_id BIGSERIAL PRIMARY KEY,
    order_id      INTEGER REFERENCES orders (order_id),
    product_id    INTEGER REFERENCES products (product_id),
    quantity      INTEGER        NOT NULL,
    unit_price    DECIMAL(10, 2) NOT NULL
);

-- Invoices Table
CREATE TABLE invoices
(
    invoice_id     BIGSERIAL PRIMARY KEY,
    order_id       INTEGER REFERENCES orders (order_id),
    invoice_date   DATE           NOT NULL,
    due_date       DATE           NOT NULL,
    amount_paid    DECIMAL(10, 2) NOT NULL,
    payment_status VARCHAR(50)    NOT NULL
);

CREATE TABLE users
(
    user_id    BIGSERIAL PRIMARY KEY,
    username   VARCHAR(255) UNIQUE,
    password   VARCHAR(255),
    enabled    BOOLEAN,
    role       VARCHAR(155),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Employees Table
CREATE TABLE employees
(
    employee_id  BIGSERIAL PRIMARY KEY,
    user_id      INTEGER REFERENCES users (user_id),
    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    phone_number VARCHAR(255),
    department   VARCHAR(255),
    birth_date   DATE,
    active       BOOLEAN,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE photo
(
    id         BIGSERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES products (product_id),
    name       VARCHAR(255),
    url        VARCHAR(255),
    file_size  INTEGER
);

-- Tasks Table
CREATE TABLE tasks
(
    task_id    SERIAL PRIMARY KEY,
    name       VARCHAR(255),
    completed  BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);