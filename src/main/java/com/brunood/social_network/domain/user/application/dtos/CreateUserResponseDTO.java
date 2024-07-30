package com.brunood.social_network.domain.user.application.dtos;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class CreateUserResponseDTO {
    private String username;
    private String email;
    private LocalDate birthDayDate;
}
