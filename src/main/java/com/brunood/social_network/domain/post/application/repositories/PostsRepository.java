package com.brunood.social_network.domain.post.application.repositories;

import com.brunood.social_network.domain.post.application.dtos.CreatePostRequestDTO;
import com.brunood.social_network.domain.post.enterprise.entities.PostEntity;

import java.util.List;

public interface PostsRepository {

    PostEntity createPost(Long userId, CreatePostRequestDTO data);
    List<PostEntity> findManyByUserId(Long userId);
}
