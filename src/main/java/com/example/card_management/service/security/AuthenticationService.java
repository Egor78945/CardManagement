package com.example.card_management.service.security;

import com.example.card_management.model.user.security.UserCredentialDTO;

public interface AuthenticationService<U extends UserCredentialDTO> {
    void register(U user);

    String authenticate(U user);
}
