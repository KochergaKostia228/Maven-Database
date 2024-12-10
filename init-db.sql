CREATE TABLE notice (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    type VARCHAR(50),
    processed BOOLEAN DEFAULT FALSE
);