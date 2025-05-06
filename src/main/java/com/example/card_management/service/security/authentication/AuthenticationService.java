package com.example.card_management.service.security.authentication;

import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.model.user.credential.dto.UserCredentialDTO;
import com.example.card_management.service.security.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Tag(name = "AuthenticationService", description = "Сервис, предоставляющий основной функционал для реализации аутентификации пользователей")
public abstract class AuthenticationService<U extends UserCredentialDTO> {
    protected final TokenService<UserCredential> tokenService;

    public AuthenticationService(@Qualifier("JWTService") TokenService<UserCredential> tokenService) {
        this.tokenService = tokenService;
    }

    public abstract void register(U user);

    @Operation(description = "Аутентифицировать пользователя и вернуть access token")
    public abstract String authenticate(U user);

    @Operation(description = "Получить email текущего пользователя из authentication сессии")
    public abstract String getCurrentUserEmail();

    @Operation(description = "Сравнить email пользователя в authentication сессии с другим")
    public boolean checkRights(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (email == null || authentication == null || authentication.getPrincipal() == null) {
            return false;
        }
        return email.equals(authentication.getPrincipal());
    }

}
