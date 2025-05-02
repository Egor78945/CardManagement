package com.example.card_management.configuration.security.filter;

import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.service.security.token.TokenService;
import com.example.card_management.service.user.credential.UserCredentialService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final TokenService<UserCredential> tokenService;
    private final UserCredentialService<UserCredential> userCredentialService;

    public JWTFilter(@Qualifier("JWTService") TokenService<UserCredential> tokenService, @Qualifier("userCredentialServiceManager") UserCredentialService<UserCredential> userCredentialService) {
        this.tokenService = tokenService;
        this.userCredentialService = userCredentialService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenService.isTokenValid(token) || !userCredentialService.existsByEmail(tokenService.extractEmailFromToken(token))) {
                String email = tokenService.extractEmailFromToken(token);
                List<String> authorities = tokenService.extractAuthoritiesFromToken(token);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, null, authorities
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
