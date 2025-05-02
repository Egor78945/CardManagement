package com.example.card_management.service.security.authentication;

import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.model.user.security.UserCredentialDTO;
import com.example.card_management.service.security.token.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AuthenticationService<U extends UserCredentialDTO> {
    protected final TokenService<UserCredential> tokenService;

    public AuthenticationService(@Qualifier("JWTService") TokenService<UserCredential> tokenService) {
        this.tokenService = tokenService;
    }

    public abstract void register(U user);

    public abstract String authenticate(U user);

    public abstract String getCurrentUserEmail();

    public boolean checkRights(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (email == null || authentication == null || authentication.getPrincipal() == null) {
            return false;
        }
        return email.equals(authentication.getPrincipal());
    }

}
