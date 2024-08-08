package com.brunood.social_network.domain.user.application.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateResponseDTO;
import com.brunood.social_network.domain.user.application.dtos.CreateRefreshTokenDTO;
import com.brunood.social_network.domain.user.application.dtos.RefreshSessionDTO;
import com.brunood.social_network.domain.user.application.repositories.RefreshTokensRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RefreshUserSessionUseCase {

    @Setter
    @Value("${security.token.secret}")
    private String secretKey;

    private final RefreshTokensRepository refreshTokensRepository;

    public RefreshUserSessionUseCase(RefreshTokensRepository refreshTokensRepository) {
        this.refreshTokensRepository = refreshTokensRepository;
    }

    public AuthenticateResponseDTO execute(RefreshSessionDTO data) {
        var refreshTokenExists = this.refreshTokensRepository.findByRefreshToken(data.refreshToken());
        if (refreshTokenExists == null) throw new BusinessException("401", "Invalid refresh token");
        if (refreshTokenExists.getExpiryDate().isBefore(LocalDateTime.now())) throw new BusinessException("401", "Invalid refresh token");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("devnet")
                .withExpiresAt(expiresIn)
                .withSubject(refreshTokenExists.getUser().getId().toString())
                .withClaim("roles", List.of("USER"))
                .sign(algorithm);

        var newRefreshToken = this.refreshTokensRepository.createRefreshToken(
                CreateRefreshTokenDTO
                        .builder()
                        .userId(refreshTokenExists.getUser().getId())
                        .build());

        return AuthenticateResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .refreshToken(newRefreshToken.getToken())
                .build();
    }
}
