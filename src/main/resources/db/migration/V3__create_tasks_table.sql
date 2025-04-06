-- Tasks Table
CREATE TABLE tasks (
    task_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    completed BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);