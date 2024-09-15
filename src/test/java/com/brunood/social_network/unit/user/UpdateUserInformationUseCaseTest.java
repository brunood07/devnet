package com.brunood.social_network.unit.user;

import com.brunood.social_network.core.exception.custom.RecordNotFoundException;
import com.brunood.social_network.domain.user.application.dtos.UpdateUserInformationRequestDTO;
import com.brunood.social_network.domain.user.application.dtos.UserInformationDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.application.usecases.UpdateUserInformationUseCase;
import com.brunood.social_network.domain.user.enterprise.entities.UserEntity;
import com.brunood.social_network.infra.database.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserInformationUseCaseTest {

    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UpdateUserInformationUseCase updateUserInformationUseCase;

    @Test
    void givenValidParameters_whenUpdateUserInformation_thenReturnUpdatedUser() {
        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(
                UserEntity.builder()
                        .birthDayDate(LocalDate.now())
                        .email("test@email.com")
                        .isActive(true)
                        .username("test-username")
                        .build()
        ));
        when(usersRepository.update(any())).thenReturn(UserEntity.builder()
                .birthDayDate(LocalDate.now())
                .email("test@email.com")
                .isActive(true)
                .username("test-username")
                .build());

        var response = updateUserInformationUseCase.execute(1L, UpdateUserInformationRequestDTO.builder().birthDayDate(LocalDate.now()).build());

        verify(usersRepository, times(1)).findById(anyLong());
        verify(usersRepository, times(1)).update(any());

        assertInstanceOf(UserInformationDTO.class, response);
    }

    @Test
    void givenInvalidId_whenUpdateUserInformation_thenThrowError() {
        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> updateUserInformationUseCase.execute(1L, UpdateUserInformationRequestDTO.builder().birthDayDate(LocalDate.now()).build()));

        verify(usersRepository, times(1)).findById(anyLong());
    }
}
