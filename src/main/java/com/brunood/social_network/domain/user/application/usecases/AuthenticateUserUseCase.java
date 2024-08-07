package com.brunood.social_network.domain.user.application.usecases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateRequestDTO;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateResponseDTO;
import com.brunood.social_network.domain.user.application.dtos.CreateRefreshTokenDTO;
import com.brunood.social_network.domain.user.application.repositories.RefreshTokensRepository;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthenticateUserUseCase {

    @Setter
    @Value("${security.token.secret}")
    private String secretKey;

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokensRepository refreshTokensRepository;

    public AuthenticateUserUseCase(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RefreshTokensRepository refreshTokensRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokensRepository = refreshTokensRepository;
    }

    public AuthenticateResponseDTO execute(AuthenticateRequestDTO data) {
        var user = this.usersRepository.findByEmail(data.email()).orElseThrow(() -> new BusinessException("400", "Invalid user"));
        boolean passwordMatches = this.passwordEncoder.matches(data.password(), user.getPassword());
        if (!passwordMatches) throw new BusinessException("400", "Invalid user");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("devnet")
                .withExpiresAt(expiresIn)
                .withSubject(user.getId().toString())
                .withClaim("roles", List.of("USER"))
                .sign(algorithm);

        var refreshToken = this.refreshTokensRepository.createRefreshToken(
                CreateRefreshTokenDTO
                        .builder()
                        .userId(user.getId())
                        .build());

        return AuthenticateResponseDTO.builder()
                .accessToken(token)
                .expiresIn(expiresIn.toEpochMilli())
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
