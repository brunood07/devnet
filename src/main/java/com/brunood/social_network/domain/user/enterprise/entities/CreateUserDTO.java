package com.brunood.social_network.domain.user.enterprise.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class CreateUserDTO {

    @NotNull(message = "username cannot be null")
    private String username;
    @Email
    private String email;
    private LocalDate birthDayDate;
    private String password;
}
