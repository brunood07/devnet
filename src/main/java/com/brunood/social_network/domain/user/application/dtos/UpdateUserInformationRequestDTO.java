package com.brunood.social_network.domain.user.application.dtos;

import java.time.LocalDate;

public record UpdateUserInformationRequestDTO(
    LocalDate birthDayDate
) {
}
