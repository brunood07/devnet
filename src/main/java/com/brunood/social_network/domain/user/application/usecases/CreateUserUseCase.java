package com.brunood.social_network.domain.user.application.usecases;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.user.application.dtos.CreateUserResponseDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CreateUserResponseDTO execute(CreateUserDTO data) {
        this.usersRepository
                .findByEmail(data.getEmail())
                .ifPresent(user -> {
                    throw new BusinessException("422", "Invalid email");
                });

        String encodedPassword = passwordEncoder.encode(data.getPassword());
        data.setPassword(encodedPassword);

        var createdUser = this.usersRepository.createUser(data);
        return CreateUserResponseDTO.builder()
                .email(createdUser.getEmail())
                .birthDayDate(createdUser.getBirthDayDate())
                .username(createdUser.getUsername())
                .build();
    }
}
