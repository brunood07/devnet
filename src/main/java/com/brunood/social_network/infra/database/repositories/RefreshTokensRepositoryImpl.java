package com.brunood.social_network.infra.database.repositories;

import com.brunood.social_network.core.exception.custom.RecordNotFoundException;
import com.brunood.social_network.domain.user.application.dtos.CreateRefreshTokenDTO;
import com.brunood.social_network.domain.user.application.repositories.RefreshTokensRepository;
import com.brunood.social_network.infra.database.entities.RefreshToken;
import com.brunood.social_network.infra.database.jpa.JpaRefreshTokensRepository;
import com.brunood.social_network.infra.database.jpa.JpaUsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class RefreshTokensRepositoryImpl implements RefreshTokensRepository {

    private final JpaRefreshTokensRepository jpaRefreshTokensRepository;
    private final JpaUsersRepository jpaUsersRepository;

    @Value("${security.refresh.expiry}")
    private Long refreshTokenDurationMs;

    public RefreshTokensRepositoryImpl(JpaRefreshTokensRepository jpaRefreshTokensRepository, JpaUsersRepository jpaUsersRepository) {
        this.jpaRefreshTokensRepository = jpaRefreshTokensRepository;
        this.jpaUsersRepository = jpaUsersRepository;
    }

    @Override
    public RefreshToken createRefreshToken(CreateRefreshTokenDTO data) {
        RefreshToken refreshToken = new RefreshToken();
        var user = this.jpaUsersRepository.findById(data.getUserId()).orElse(null);
        if (user == null) throw new RecordNotFoundException();
        return this.jpaRefreshTokensRepository.save(
                RefreshToken
                        .builder()
                        .expiryDate(LocalDateTime.now().plusDays(refreshTokenDurationMs))
                        .token(UUID.randomUUID().toString())
                        .user(user)
                        .build());
    }

    @Override
    public RefreshToken findByRefreshToken(String refreshToken) {
        return this.jpaRefreshTokensRepository.findByToken(refreshToken).orElse(null);
    }
}
