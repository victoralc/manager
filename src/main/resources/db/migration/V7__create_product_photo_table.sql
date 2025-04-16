CREATE TABLE photo (
    id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES products(product_id),
    name VARCHAR(255),
    url VARCHAR(255),
    file_size INTEGER
)