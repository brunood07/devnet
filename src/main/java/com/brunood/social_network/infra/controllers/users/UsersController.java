package com.brunood.social_network.infra.controllers.users;

import com.brunood.social_network.core.exception.response.standard.ResponseDTO;
import com.brunood.social_network.core.exception.response.standard.ResponseType;
import com.brunood.social_network.core.exception.response.standard.StandardResponse;
import com.brunood.social_network.domain.user.application.usecases.CreateUserUseCase;
import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UsersController implements UsersResource {
    private final CreateUserUseCase createUserUseCase;

    public UsersController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    public ResponseEntity<ResponseDTO> signup(CreateUserDTO data, HttpServletRequest httpServletRequest) {
        var createUser = this.createUserUseCase.execute(data);
        return StandardResponse.generateResponse(
                "user created",
                null,
                ResponseType.SUCCESS,
                HttpStatus.CREATED,
                this.createUserUseCase.execute(data)
        );
    }
}
