package com.brunood.social_network.domain.user.application.dtos;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserInformationDTO(
        Long id,
        String username,
        String email,
        LocalDate birthDayDate
) {
}
