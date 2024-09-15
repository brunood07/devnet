package com.brunood.social_network.unit.user;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.user.application.dtos.CreateUserDTO;
import com.brunood.social_network.domain.user.application.dtos.CreateUserResponseDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.application.usecases.CreateUserUseCase;
import com.brunood.social_network.domain.user.enterprise.entities.UserEntity;
import com.brunood.social_network.infra.database.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    void givenValidData_whenCreateUser_thenCreateUser() {
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
        when(usersRepository.createUser(any())).thenReturn(
                UserEntity.builder()
                        .birthDayDate(LocalDate.now())
                        .createdAt(LocalDateTime.now())
                        .email("test@email.com")
                        .isActive(true)
                        .password("encoded-password")
                        .username("testname")
                        .build()
                );

        CreateUserResponseDTO response = createUserUseCase.execute(CreateUserDTO.builder()
                .birthDayDate(LocalDate.now())
                .email("test@email.com")
                .password("test-password")
                .username("testname")
                .build());

        verify(usersRepository, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usersRepository, times(1)).createUser(any());

        assertInstanceOf(CreateUserResponseDTO.class, response);
        assertEquals("test@email.com", response.getEmail());
    }

    @Test
    void givenValidDataAndUserAlreadyExists_whenCreateUser_thenThrowBusinessException() {
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(UserEntity.builder()
                .birthDayDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .email("test@email.com")
                .isActive(true)
                .password("encoded-password")
                .username("testname")
                .build()));

        assertThrows(BusinessException.class, () -> createUserUseCase.execute(CreateUserDTO.builder()
                .birthDayDate(LocalDate.now())
                .email("test@email.com")
                .password("test-password")
                .username("testname")
                .build()));

        verify(usersRepository, times(1)).findByEmail(anyString());
    }

}
