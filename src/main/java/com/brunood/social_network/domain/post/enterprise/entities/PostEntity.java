package com.brunood.social_network.domain.post.enterprise.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class PostEntity {

    private String id;
    private Long userId;

    @NotNull(message = "")
    @Max(value = 140, message = "")
    private String content;

    private LocalDateTime createdAt;
}
