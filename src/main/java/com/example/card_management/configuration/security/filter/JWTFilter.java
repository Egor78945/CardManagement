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
            String email = tokenService.extractEmailFromToken(token);
            if (tokenService.isTokenValid(token) && email != null && userCredentialService.existsByEmail(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, null, tokenService.extractAuthoritiesFromToken(token)
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
