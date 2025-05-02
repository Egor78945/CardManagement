package com.example.card_management.service.security.token;

import com.example.card_management.configuration.security.environment.JWTEnvironment;
import com.example.card_management.model.user.credential.entity.UserCredential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTService implements TokenService<UserCredential> {
    private final JWTEnvironment jwtEnvironment;

    public JWTService(JWTEnvironment jwtEnvironment) {
        this.jwtEnvironment = jwtEnvironment;
    }

    @Override
    public String generateToken(UserCredential credential) {
        return Jwts.builder()
                .claims(Map.of("roles", credential.getRoleList()
                        .stream()
                        .map(r -> r.getRole().getRole())
                        .toList()))
                .subject(credential.getEmail())
                .expiration(new Date(System.currentTimeMillis() + jwtEnvironment.getJWT_LIFETIME()))
                .signWith(jwtEnvironment.getJWT_SECRET())
                .compact();
    }

    @Override
    public String extractEmailFromToken(String token) {
        return extractClaimsFromToken(token).getSubject();
    }

    @Override
    public List<String> extractAuthoritiesFromToken(String token) {
        return extractClaimsFromToken(token).get("roles", List.class);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaimsFromToken(token);
            return claims.get("roles", List.class) != null && claims.getSubject() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaimsFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(jwtEnvironment.getJWT_SECRET())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
