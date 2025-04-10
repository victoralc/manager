-- Users Table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    enabled BOOLEAN,
    role VARCHAR(155),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);