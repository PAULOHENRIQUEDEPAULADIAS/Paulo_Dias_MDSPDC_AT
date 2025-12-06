CREATE TABLE IF NOT EXISTS call_log (
    id SERIAL PRIMARY KEY,
    service_name VARCHAR(50) NOT NULL,
    response VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);