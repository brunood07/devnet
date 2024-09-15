package com.brunood.social_network.infra.database.jpa;

import com.brunood.social_network.infra.database.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaPostsRepository extends JpaRepository<Post, UUID> {

    List<Post> findManyByUserId(Long userId);
}
