CREATE SCHEMA IF NOT EXISTS social_network;

CREATE TABLE social_network.tb_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birth_day_date DATE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);