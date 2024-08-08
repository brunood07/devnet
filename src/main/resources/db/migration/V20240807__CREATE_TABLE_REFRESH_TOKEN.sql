CREATE TABLE social_network.tb_refresh_tokens (
  id BIGSERIAL PRIMARY KEY,
   user_id BIGINT NOT NULL,
   token VARCHAR(255) NOT NULL,
   expiry_date TIMESTAMP NOT NULL,
   CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES social_network.tb_users(id)
);