package com.example.card_management.service.security.token;

import com.example.card_management.configuration.security.environment.JWTEnvironment;
import com.example.card_management.model.user.credential.entity.UserCredential;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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
                .issuedAt(new Date(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + jwtEnvironment.getJWT_LIFETIME()))
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
    public boolean isTokenExpired(String token) {
        return extractClaimsFromToken(token).getExpiration().before(new Date());
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(jwtEnvironment.getJWT_SECRET())  // Проверка подписи
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Claims extractClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(jwtEnvironment.getJWT_SECRET())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
