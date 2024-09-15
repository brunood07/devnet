CREATE TABLE social_network.tb_posts (
    id UUID PRIMARY KEY,
    content VARCHAR(140) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES social_network.tb_users(id)
);