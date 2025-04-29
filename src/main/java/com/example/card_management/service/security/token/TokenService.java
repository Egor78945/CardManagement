package com.example.card_management.service.security.token;

import com.example.card_management.model.user.credential.entity.UserCredential;

import java.util.List;

public interface TokenService <C extends UserCredential> {
    String generateToken(C credential);

    String extractEmailFromToken(String token);

    List<String> extractAuthoritiesFromToken(String token);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token);
}
