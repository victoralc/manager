-- Users Table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    enabled BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);