package com.brunood.social_network.infra.controllers.posts;

import com.brunood.social_network.core.exception.response.standard.ResponseDTO;
import com.brunood.social_network.domain.post.application.dtos.CreatePostRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public interface PostsResource {

    @PostMapping
    ResponseEntity<ResponseDTO> createPost(@Valid @RequestBody CreatePostRequestDTO data, HttpServletRequest httpServletRequest);
}
