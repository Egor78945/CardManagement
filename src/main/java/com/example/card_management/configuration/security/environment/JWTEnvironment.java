package com.example.card_management.configuration.security.environment;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTEnvironment {
    private final long JWT_LIFETIME;

    public JWTEnvironment(@Value("${security.jwt.lifetime}") long JWT_LIFETIME) {
        this.JWT_LIFETIME = JWT_LIFETIME;
    }

    public long getJWT_LIFETIME() {
        return JWT_LIFETIME;
    }

    public SecretKey getJWT_SECRET() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_SECRET")));
    }
}
