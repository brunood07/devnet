package com.brunood.social_network.domain.user.application.usecases;

import com.brunood.social_network.core.exception.custom.RecordNotFoundException;
import com.brunood.social_network.domain.user.application.dtos.UserInformationDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserInformationUseCase {

    private final UsersRepository usersRepository;

    public GetUserInformationUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserInformationDTO execute(Long id) {
        var user = this.usersRepository.findById(id).orElse(null);
        if (user == null) throw new RecordNotFoundException();

        return UserInformationDTO
                .builder()
                .birthDayDate(user.getBirthDayDate())
                .email(user.getEmail())
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
