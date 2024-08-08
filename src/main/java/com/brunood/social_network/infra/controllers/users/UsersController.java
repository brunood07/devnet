package com.brunood.social_network.infra.controllers.users;

import com.brunood.social_network.core.exception.response.standard.ResponseDTO;
import com.brunood.social_network.core.exception.response.standard.ResponseType;
import com.brunood.social_network.core.exception.response.standard.StandardResponse;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateRequestDTO;
import com.brunood.social_network.domain.user.application.dtos.RefreshSessionDTO;
import com.brunood.social_network.domain.user.application.dtos.UpdateUserInformationRequestDTO;
import com.brunood.social_network.domain.user.application.usecases.*;
import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersController implements UsersResource {
    private final CreateUserUseCase createUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RefreshUserSessionUseCase refreshUserSessionUseCase;
    private final GetUserInformationUseCase getUserInformationUseCase;
    private final UpdateUserInformationUseCase updateUserInformationUseCase;

    public UsersController(
            CreateUserUseCase createUserUseCase,
            AuthenticateUserUseCase authenticateUserUseCase,
            RefreshUserSessionUseCase refreshUserSessionUseCase,
            GetUserInformationUseCase getUserInformationUseCase,
            UpdateUserInformationUseCase updateUserInformationUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.refreshUserSessionUseCase = refreshUserSessionUseCase;
        this.getUserInformationUseCase = getUserInformationUseCase;
        this.updateUserInformationUseCase = updateUserInformationUseCase;
    }

    @Override
    public ResponseEntity<ResponseDTO> signup(@Valid CreateUserDTO data, HttpServletRequest httpServletRequest) {
        return StandardResponse.generateResponse(
                "user created",
                null,
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                this.createUserUseCase.execute(data)
        );
    }

    @Override
    public ResponseEntity<ResponseDTO> signin(AuthenticateRequestDTO data, HttpServletRequest httpServletRequest) {
        return StandardResponse.generateResponse(
                "authentication",
                null,
                ResponseType.SUCCESS,
                HttpStatus.OK,
                this.authenticateUserUseCase.execute(data)
        );
    }

    @Override
    public ResponseEntity<ResponseDTO> refreshSession(RefreshSessionDTO data, HttpServletRequest httpServletRequest) {
        return StandardResponse.generateResponse(
                "authentication",
                null,
                ResponseType.SUCCESS,
                HttpStatus.OK,
                this.refreshUserSessionUseCase.execute(data)
        );
    }

    @Override
    public ResponseEntity<ResponseDTO> getUserInformation(HttpServletRequest httpServletRequest) {
        var clientId = httpServletRequest.getAttribute("client_id");
        return StandardResponse.generateResponse(
                "authentication",
                null,
                ResponseType.SUCCESS,
                HttpStatus.OK,
                this.getUserInformationUseCase.execute(Long.valueOf(clientId.toString()))
        );
    }

    @Override
    public ResponseEntity<ResponseDTO> updateUserInformation(UpdateUserInformationRequestDTO data, HttpServletRequest httpServletRequest) {
        var clientId = httpServletRequest.getAttribute("client_id");
        return StandardResponse.generateResponse(
                "authentication",
                null,
                ResponseType.SUCCESS,
                HttpStatus.OK,
                this.updateUserInformationUseCase.execute(Long.valueOf(clientId.toString()), data)
        );
    }
}
