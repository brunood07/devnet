package com.brunood.social_network.unit.user;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateResponseDTO;
import com.brunood.social_network.domain.user.application.dtos.RefreshSessionDTO;
import com.brunood.social_network.domain.user.application.repositories.RefreshTokensRepository;
import com.brunood.social_network.domain.user.application.usecases.RefreshUserSessionUseCase;
import com.brunood.social_network.infra.database.entities.RefreshToken;
import com.brunood.social_network.infra.database.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefreshUserSessionUseCaseTest {

    @Mock
    private RefreshTokensRepository refreshTokensRepository;
    @InjectMocks
    private RefreshUserSessionUseCase refreshUserSessionUseCase;

    private static final String SECRET_KEY = "your-secret-key";

    @BeforeEach
    void setUp() {
        refreshUserSessionUseCase.setSecretKey(SECRET_KEY);
    }

    @Test
    void givenValidRefreshToken_whenRefreshUserSession_thenReturnAccessToken() {
        when(refreshTokensRepository.findByRefreshToken(anyString())).thenReturn(
                RefreshToken
                        .builder()
                        .expiryDate(LocalDateTime.now().plusDays(1l))
                        .id(1L)
                        .token("test-refresh-token")
                        .user(User.builder()
                                .birthDayDate(LocalDate.now())
                                .createdAt(LocalDateTime.now())
                                .email("test@email.com")
                                .isActive(true)
                                .id(1L)
                                .password("encoded-password")
                                .username("testname")
                                .build())
                        .build()
        );
        when(refreshTokensRepository.createRefreshToken(any())).thenReturn(RefreshToken
                .builder()
                .token("test-refresh-token-2")
                .build()
        );

        AuthenticateResponseDTO response = refreshUserSessionUseCase.execute(RefreshSessionDTO.builder().refreshToken("test-refresh-token").build());

        verify(refreshTokensRepository, times(1)).findByRefreshToken(anyString());
        verify(refreshTokensRepository, times(1)).createRefreshToken(any());

        assertInstanceOf(AuthenticateResponseDTO.class, response);
        assertNotNull(response.getAccessToken());
    }

    @Test
    void givenNonExistentRefreshToken_whenRefreshUserSession_thenThrowBusinessException() {
        when(refreshTokensRepository.findByRefreshToken(anyString())).thenReturn(
                RefreshToken
                        .builder()
                        .expiryDate(LocalDateTime.now().minusDays(1l))
                        .id(1L)
                        .token("test-refresh-token")
                        .user(User.builder()
                                .birthDayDate(LocalDate.now())
                                .createdAt(LocalDateTime.now())
                                .email("test@email.com")
                                .isActive(true)
                                .id(1L)
                                .password("encoded-password")
                                .username("testname")
                                .build())
                        .build()
        );

        assertThrows(BusinessException.class, () -> refreshUserSessionUseCase.execute(RefreshSessionDTO.builder().refreshToken("test-refresh-token").build()));

        verify(refreshTokensRepository, times(1)).findByRefreshToken(anyString());
    }

    @Test
    void givenExpiredRefreshToken_whenRefreshUserSession_thenThrowBusinessException() {
        assertThrows(BusinessException.class, () -> refreshUserSessionUseCase.execute(RefreshSessionDTO.builder().refreshToken("test-refresh-token").build()));
    }
}
