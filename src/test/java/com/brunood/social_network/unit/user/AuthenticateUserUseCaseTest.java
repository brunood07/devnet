package com.brunood.social_network.unit.user;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateRequestDTO;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateResponseDTO;
import com.brunood.social_network.domain.user.application.repositories.UsersRepository;
import com.brunood.social_network.domain.user.application.usecases.AuthenticateUserUseCase;
import com.brunood.social_network.infra.database.entities.User;
import org.junit.jupiter.api.BeforeEach;
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
public class AuthenticateUserUseCaseTest {

    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthenticateUserUseCase authenticateUserUseCase;

    private static final String SECRET_KEY = "your-secret-key";

    @BeforeEach
    void setUp() {
        authenticateUserUseCase.setSecretKey(SECRET_KEY);
    }

    @Test
    void givenValidData_whenAuthenticateUser_thenReturnAccessToken() {
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(User.builder()
                .birthDayDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .email("test@email.com")
                .isActive(true)
                .password("encoded-password")
                .username("testname")
                .build()));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        AuthenticateResponseDTO response = authenticateUserUseCase.execute(
                AuthenticateRequestDTO
                        .builder()
                        .email("test@email.com")
                        .password("test-pass")
                        .build()
        );

        verify(usersRepository, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());

        assertInstanceOf(AuthenticateResponseDTO.class, response);
        assertNotNull(response.getAccessToken());
    }

    @Test
    void givenNotRegisteredEmail_whenAuthenticateUser_thenThrowBusinessException() {
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> authenticateUserUseCase.execute(
                AuthenticateRequestDTO
                        .builder()
                        .email("test@email.com")
                        .password("test-pass")
                        .build()
        ));
    }
}
