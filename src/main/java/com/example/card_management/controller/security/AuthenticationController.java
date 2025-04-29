package com.example.card_management.controller.security;

import com.example.card_management.model.user.security.UserRegistrationDTO;
import com.example.card_management.service.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationService<UserRegistrationDTO> authenticationService;

    public AuthenticationController(@Qualifier("authenticationServiceManager") AuthenticationService<UserRegistrationDTO> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/registration")
    public void registration(@RequestBody UserRegistrationDTO user) {
        authenticationService.register(user);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return ResponseEntity.ok(authenticationService.authenticate(email, password));
    }
}
