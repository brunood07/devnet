package com.brunood.social_network.infra.controllers.posts;

import com.brunood.social_network.core.exception.response.standard.ResponseDTO;
import com.brunood.social_network.core.exception.response.standard.ResponseType;
import com.brunood.social_network.core.exception.response.standard.StandardResponse;
import com.brunood.social_network.domain.post.application.dtos.CreatePostRequestDTO;
import com.brunood.social_network.domain.post.application.usecases.CreatePostUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PostsController implements PostsResource {

    private final CreatePostUseCase createPostUseCase;

    public PostsController(
            CreatePostUseCase createPostUseCase
    ) {
        this.createPostUseCase = createPostUseCase;
    }

    @Override
    public ResponseEntity<ResponseDTO> createPost(CreatePostRequestDTO data, HttpServletRequest httpServletRequest) {
        var userId = httpServletRequest.getAttribute("user_id");
        return StandardResponse.generateResponse(
                "user created",
                null,
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                this.createPostUseCase.execute(Long.valueOf(userId.toString()), data)
        );
    }
}
