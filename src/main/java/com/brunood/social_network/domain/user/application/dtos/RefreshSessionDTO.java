package com.brunood.social_network.domain.user.application.dtos;

import lombok.Builder;

@Builder
public record RefreshSessionDTO(String refreshToken) {
}
