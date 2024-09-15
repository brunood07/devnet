package com.brunood.social_network.domain.post.application.usecases;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.post.application.dtos.CreatePostRequestDTO;
import com.brunood.social_network.domain.post.application.repositories.PostsRepository;
import com.brunood.social_network.domain.post.enterprise.entities.PostEntity;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class CreatePostUseCase {

    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    public CreatePostUseCase(UsersRepository usersRepository, PostsRepository postsRepository) {
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
    }

    public PostEntity execute(Long userId, CreatePostRequestDTO data) {
        var userExists = this.usersRepository.findById(userId);
        if (userExists.isEmpty()) {
            throw new BusinessException("User not found", "404");
        }
        var createdPost = this.postsRepository.createPost(userId, data);

        return PostEntity.builder()
                .content(createdPost.getContent())
                .userId(userId)
                .createdAt(createdPost.getCreatedAt())
                .build();
    }
}
