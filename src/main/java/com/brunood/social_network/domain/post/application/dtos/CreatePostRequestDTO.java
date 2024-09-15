package com.brunood.social_network.domain.post.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequestDTO {

    @NotBlank(message = "Content cannot be blank")
    @Size(max = 140, message = "Content must be less than 140 characters")
    private String content;
}
