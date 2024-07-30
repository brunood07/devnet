package com.brunood.social_network.domain.user.application.repositories;

import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import com.brunood.social_network.infra.database.entities.User;

import java.util.Optional;

public interface UsersRepository {
    User createUser(CreateUserDTO data);
    Optional<User> findByEmail(String email);
}
