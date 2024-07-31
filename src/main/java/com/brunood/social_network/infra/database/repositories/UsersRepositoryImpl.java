package com.brunood.social_network.infra.database.repositories;

import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import com.brunood.social_network.infra.database.entities.User;
import com.brunood.social_network.infra.database.jpa.JpaUsersRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {
    private final JpaUsersRepository jpaUsersRepository;

    public UsersRepositoryImpl(JpaUsersRepository jpaUsersRepository) {
        this.jpaUsersRepository = jpaUsersRepository;
    }

    @Override
    public User createUser(CreateUserDTO data) {
        return this.jpaUsersRepository.save(
                User.builder()
                        .email(data.getEmail())
                        .isActive(true)
                        .birthDayDate(data.getBirthDayDate())
                        .password(data.getPassword())
                        .username(data.getUsername())
                        .build()
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.jpaUsersRepository.findFirstByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.jpaUsersRepository.findByIdAndIsActiveTrue(id);
    }

    @Override
    public User update(User data) {
        return this.jpaUsersRepository.save(data);
    }
}
