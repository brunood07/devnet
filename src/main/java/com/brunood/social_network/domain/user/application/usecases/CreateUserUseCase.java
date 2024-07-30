package com.brunood.social_network.domain.user.application.usecases;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.user.application.dtos.CreateUserResponseDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import com.brunood.social_network.infra.database.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserUseCase {

    private final UsersRepository usersRepository;

    public CreateUserUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public CreateUserResponseDTO execute(CreateUserDTO data) {
        Optional<User> emailRegistered = this.usersRepository.findByEmail(data.getEmail());
        if (!emailRegistered.isEmpty()) {
            throw new BusinessException("422", "Email already registered");
        }

        var createduser = this.usersRepository.createUser(data);
        return CreateUserResponseDTO.builder()
                .email(createduser.getEmail())
                .birthDayDate(createduser.getBirthDayDate())
                .username(createduser.getUsername())
                .build();

    }
}
