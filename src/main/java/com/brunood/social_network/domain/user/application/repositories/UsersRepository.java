package com.brunood.social_network.domain.user.application.repositories;

import com.brunood.social_network.domain.user.application.dtos.CreateUserDTO;
import com.brunood.social_network.domain.user.enterprise.entities.UserEntity;

import java.util.Optional;

public interface UsersRepository {
    UserEntity createUser(CreateUserDTO data);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findById(Long id);
    UserEntity update(UserEntity data);
}
