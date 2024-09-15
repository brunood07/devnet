package com.brunood.social_network.domain.user.application.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class CreateRefreshTokenDTO {

    Long userId;
}
