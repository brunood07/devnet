package com.brunood.social_network.infra.database.jpa;

import com.brunood.social_network.infra.database.entities.RefreshToken;
import com.brunood.social_network.infra.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRefreshTokensRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
