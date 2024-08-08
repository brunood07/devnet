package com.brunood.social_network.domain.user.application.repositories;

import com.brunood.social_network.domain.user.application.dtos.CreateRefreshTokenDTO;
import com.brunood.social_network.infra.database.entities.RefreshToken;

public interface RefreshTokensRepository {
    RefreshToken createRefreshToken(CreateRefreshTokenDTO data);
    RefreshToken findByRefreshToken(String refreshToken);
}
