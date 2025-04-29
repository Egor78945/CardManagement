package com.example.card_management.service.security;

import com.example.card_management.model.user.security.UserRegistrationDTO;

public interface AuthenticationService<U extends UserRegistrationDTO> {
    void register(U user);

    String authenticate(String email, String password);
}
