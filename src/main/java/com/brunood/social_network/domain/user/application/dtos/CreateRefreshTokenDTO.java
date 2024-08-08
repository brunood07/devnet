package com.brunood.social_network.domain.user.application.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class CreateRefreshTokenDTO {

    Long userId;
}
