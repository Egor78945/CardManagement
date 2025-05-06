package com.example.card_management.service.security.token;

import com.example.card_management.model.user.credential.entity.UserCredential;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "TokenService", description = "Сервис, предоставляющий основной функционал для взаимодествия с токенами")
public interface TokenService <C extends UserCredential> {
    String generateToken(C credential);

    String extractUsernameFromToken(String token);

    List<String> extractAuthoritiesFromToken(String token);

    boolean isTokenValid(String token);
}
