package com.brunood.social_network.infra.database.repositories;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.post.application.dtos.CreatePostRequestDTO;
import com.brunood.social_network.domain.post.application.repositories.PostsRepository;
import com.brunood.social_network.domain.post.enterprise.entities.PostEntity;
import com.brunood.social_network.infra.database.entities.Post;
import com.brunood.social_network.infra.database.entities.User;
import com.brunood.social_network.infra.database.jpa.JpaPostsRepository;
import com.brunood.social_network.infra.database.jpa.JpaUsersRepository;
import com.brunood.social_network.infra.database.mappers.GenericMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostsRepositoryImpl implements PostsRepository {

    private final GenericMapper mapper;
    private final JpaPostsRepository jpaPostsRepository;
    private final JpaUsersRepository jpaUsersRepository;

    public PostsRepositoryImpl(GenericMapper mapper, JpaPostsRepository jpaPostsRepository, JpaUsersRepository jpaUsersRepository) {
        this.mapper = mapper;
        this.jpaPostsRepository = jpaPostsRepository;
        this.jpaUsersRepository = jpaUsersRepository;
    }

    @Override
    public PostEntity createPost(Long userId, CreatePostRequestDTO data) {
        User user = jpaUsersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found", "404"));

        Post savedPost = this.jpaPostsRepository.save(
                Post
                        .builder()
                        .content(data.getContent())
                        .user(user)
                        .build()
        );
        return mapper.map(savedPost, PostEntity.class);
    }

    @Override
    public List<PostEntity> findManyByUserId(Long userId) {
        List<Post> userPosts = jpaPostsRepository.findManyByUserId(userId);

        return userPosts.stream()
                .map(post -> mapper.map(post, PostEntity.class))
                .collect(Collectors.toList());
    }
}
