package com.brunood.social_network.domain.user.enterprise.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class UserEntity {

    private Long id;
    private String username;
    private String email;
    private LocalDate birthDayDate;
    private String password;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
