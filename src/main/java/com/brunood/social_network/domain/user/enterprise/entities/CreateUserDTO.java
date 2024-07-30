package com.brunood.social_network.domain.user.enterprise.entities;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class CreateUserDTO {
    private String username;
    private String email;
    private LocalDate birthDayDate;
    private String password;
}
