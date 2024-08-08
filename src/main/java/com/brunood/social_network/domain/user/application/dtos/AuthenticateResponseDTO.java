package com.brunood.social_network.domain.user.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateResponseDTO {

    private String accessToken;
    private Long expiresIn;
    private String refreshToken;
}
