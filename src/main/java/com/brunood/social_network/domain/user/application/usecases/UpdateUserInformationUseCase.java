package com.brunood.social_network.domain.user.application.usecases;

import com.brunood.social_network.core.exception.custom.RecordNotFoundException;
import com.brunood.social_network.domain.user.application.dtos.UpdateUserInformationRequestDTO;
import com.brunood.social_network.domain.user.application.dtos.UserInformationDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserInformationUseCase {

    private final UsersRepository usersRepository;

    public UpdateUserInformationUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserInformationDTO execute(Long userId, UpdateUserInformationRequestDTO data) {
        var user = this.usersRepository.findById(userId).orElse(null);
        if (user == null) throw new RecordNotFoundException();
        if (data.birthDayDate() == null) {
            return UserInformationDTO
                    .builder()
                    .birthDayDate(user.getBirthDayDate())
                    .email(user.getEmail())
                    .id(user.getId())
                    .username(user.getUsername())
                    .build();
        }

        if (data.birthDayDate() != null) user.setBirthDayDate(data.birthDayDate());
        var updatedUser = this.usersRepository.update(user);
        return UserInformationDTO
                .builder()
                .birthDayDate(updatedUser.getBirthDayDate())
                .email(updatedUser.getEmail())
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .build();
    }
}
