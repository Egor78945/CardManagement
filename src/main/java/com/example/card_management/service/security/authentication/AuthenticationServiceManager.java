package com.example.card_management.service.security.authentication;

import com.example.card_management.exception.AuthenticationException;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.model.user.dto.security.UserCredentialDTO;
import com.example.card_management.service.security.token.TokenService;
import com.example.card_management.service.user.credential.UserCredentialService;
import com.example.card_management.service.user.credential.mapper.UserCredentialMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Tag(name = "AuthenticationServiceManager", description = "Базовый реализация сервиса аутентификации пользователей")
@Service
public class AuthenticationServiceManager extends AuthenticationService<UserCredentialDTO> {
    private final UserCredentialService<UserCredential> userCredentialService;
    private final UserCredentialMapper userCredentialMapper;

    public AuthenticationServiceManager(@Qualifier("userCredentialServiceManager") UserCredentialService<UserCredential> userCredentialService, @Qualifier("JWTService") TokenService<UserCredential> tokenService, UserCredentialMapper userCredentialMapper) {
        super(tokenService);
        this.userCredentialService = userCredentialService;
        this.userCredentialMapper = userCredentialMapper;
    }

    @Override
    public void register(UserCredentialDTO user) {
        userCredentialService.save(userCredentialMapper.mapTo(user));
    }

    @Override
    public String authenticate(UserCredentialDTO userCredentialDTO) {
        if (userCredentialService.existsByEmail(userCredentialDTO.getEmail()) && BCrypt.checkpw(userCredentialDTO.getPassword(), userCredentialService.getPasswordByEmail(userCredentialDTO.getEmail()))) {
            return tokenService.generateToken(userCredentialService.getByEmail(userCredentialDTO.getEmail()));
        } else {
            throw new AuthenticationException("wrong password or email");
        }
    }

    @Override
    public String getCurrentUserEmail() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser == null || currentUser.getPrincipal() == null){
            throw new AuthenticationException("user is not authenticated");
        }
        return currentUser.getPrincipal().toString();
    }
}
