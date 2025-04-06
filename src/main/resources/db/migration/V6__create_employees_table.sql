-- Employees Table
CREATE TABLE employees (
    employee_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    department VARCHAR(255),
    birth_date DATE,
    active BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);