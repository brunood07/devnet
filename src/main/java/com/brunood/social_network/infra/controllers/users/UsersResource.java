package com.brunood.social_network.infra.controllers.users;

import com.brunood.social_network.core.exception.response.standard.ResponseDTO;
import com.brunood.social_network.domain.user.application.dtos.AuthenticateRequestDTO;
import com.brunood.social_network.domain.user.application.dtos.UpdateUserInformationRequestDTO;
import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public interface UsersResource {

    @PostMapping
    ResponseEntity<ResponseDTO> signup(@Valid @RequestBody CreateUserDTO data, HttpServletRequest httpServletRequest);

    @PostMapping("/session")
    ResponseEntity<ResponseDTO> signin(@Valid @RequestBody AuthenticateRequestDTO data, HttpServletRequest httpServletRequest);

    @GetMapping("/me")
    @SecurityRequirement(name = "jwt_auth")
    ResponseEntity<ResponseDTO> getUserInformation(HttpServletRequest httpServletRequest);

    @PutMapping
    @SecurityRequirement(name = "jwt_auth")
    ResponseEntity<ResponseDTO> updateUserInformation(@Valid @RequestBody UpdateUserInformationRequestDTO data, HttpServletRequest httpServletRequest);
}
