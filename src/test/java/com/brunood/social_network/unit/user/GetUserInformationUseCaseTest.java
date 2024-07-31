package com.brunood.social_network.unit.user;

import com.brunood.social_network.core.exception.custom.RecordNotFoundException;
import com.brunood.social_network.domain.user.application.dtos.UserInformationDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.application.usecases.GetUserInformationUseCase;
import com.brunood.social_network.infra.database.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetUserInformationUseCaseTest {

    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private GetUserInformationUseCase getUserInformationUseCase;

    @Test
    void givenValidId_whenGetUserInformationUseCase_thenReturnUserInfo() {
        when(usersRepository.findById(anyLong())).thenReturn(Optional.of(
                User.builder()
                        .birthDayDate(LocalDate.now())
                        .email("test@email.com")
                        .isActive(true)
                        .username("test-username")
                        .build()
        ));

        UserInformationDTO response = getUserInformationUseCase.execute(1L);

        verify(usersRepository, times(1)).findById(anyLong());
        assertNotNull(response);
        assertInstanceOf(UserInformationDTO.class, response);
    }

    @Test
    void givenInvalidId_whenGetUserInformationUseCase_thenThrowError() {
        when(usersRepository.findById(anyLong())).thenReturn(Optional.empty());

       assertThrows(RecordNotFoundException.class, () -> getUserInformationUseCase.execute(1L));

        verify(usersRepository, times(1)).findById(anyLong());
    }
}
