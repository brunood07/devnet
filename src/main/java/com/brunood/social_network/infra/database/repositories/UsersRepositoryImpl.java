package com.brunood.social_network.infra.database.repositories;

import com.brunood.social_network.domain.user.application.dtos.CreateUserDTO;
import com.brunood.social_network.domain.user.application.dtos.CreateUserResponseDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.enterprise.entities.UserEntity;
import com.brunood.social_network.infra.database.entities.User;
import com.brunood.social_network.infra.database.jpa.JpaUsersRepository;
import com.brunood.social_network.infra.database.mappers.GenericMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private final GenericMapper mapper;
    private final JpaUsersRepository jpaUsersRepository;

    public UsersRepositoryImpl(GenericMapper mapper, JpaUsersRepository jpaUsersRepository) {
        this.mapper = mapper;
        this.jpaUsersRepository = jpaUsersRepository;
    }

    @Override
    public UserEntity createUser(CreateUserDTO data) {
        User savedUser = this.jpaUsersRepository.save(
                User.builder()
                        .email(data.getEmail())
                        .isActive(true)
                        .birthDayDate(data.getBirthDayDate())
                        .password(data.getPassword())
                        .username(data.getUsername())
                        .build()
        );

        return this.mapper.map(savedUser, UserEntity.class);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return this.jpaUsersRepository.findFirstByEmail(email)
                .map(user -> this.mapper.map(user, UserEntity.class));
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return this.jpaUsersRepository.findByIdAndIsActiveTrue(id)
                .map(user -> this.mapper.map(user, UserEntity.class));
    }

    @Override
    public UserEntity update(UserEntity data) {
        User user = this.mapper.map(data, User.class);
        User savedUser = this.jpaUsersRepository.save(user);
        return this.mapper.map(savedUser, UserEntity.class);
    }
}
