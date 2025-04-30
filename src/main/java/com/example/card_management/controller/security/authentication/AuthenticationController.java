package com.example.card_management.controller.security.authentication;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.security.authentication.advice.handler.AuthenticationControllerExceptionHandler;
import com.example.card_management.model.user.security.UserCredentialDTO;
import com.example.card_management.service.security.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@CommonControllerExceptionHandler
@AuthenticationControllerExceptionHandler
public class AuthenticationController {
    private final AuthenticationService<UserCredentialDTO> authenticationService;

    public AuthenticationController(@Qualifier("authenticationServiceManager") AuthenticationService<UserCredentialDTO> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public void registration(@Valid @RequestBody UserCredentialDTO user) {
        authenticationService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserCredentialDTO user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
}
