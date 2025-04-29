package com.example.card_management.service.security;

import com.example.card_management.exception.AuthenticationException;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.model.user.security.UserRegistrationDTO;
import com.example.card_management.service.security.token.TokenService;
import com.example.card_management.service.user.credential.UserCredentialService;
import com.example.card_management.service.user.credential.mapper.UserCredentialMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceManager implements AuthenticationService<UserRegistrationDTO> {
    private final UserCredentialService<UserCredential> userCredentialService;
    private final UserCredentialMapper userCredentialMapper;
    private final TokenService<UserCredential> tokenService;

    public AuthenticationServiceManager(@Qualifier("userCredentialServiceManager") UserCredentialService<UserCredential> userCredentialService, @Qualifier("JWTService") TokenService<UserCredential> tokenService, UserCredentialMapper userCredentialMapper) {
        this.userCredentialService = userCredentialService;
        this.tokenService = tokenService;
        this.userCredentialMapper = userCredentialMapper;
    }

    @Override
    public void register(UserRegistrationDTO user) {
        userCredentialService.save(userCredentialMapper.mapTo(user));
    }

    @Override
    public String authenticate(String email, String password) {
        if (BCrypt.checkpw(password, userCredentialService.getPasswordByEmail(email))) {
            return tokenService.generateToken(userCredentialService.getByEmail(email));
        } else {
            throw new AuthenticationException("wrong password");
        }
    }
}
