package com.brunood.social_network.infra.controllers.users;

import com.brunood.social_network.core.exception.response.standard.ResponseDTO;
import com.brunood.social_network.domain.user.enterprise.entities.CreateUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user")
public interface UsersResource {

    @PostMapping
    ResponseEntity<ResponseDTO> signup(@RequestBody CreateUserDTO data, HttpServletRequest httpServletRequest);
}