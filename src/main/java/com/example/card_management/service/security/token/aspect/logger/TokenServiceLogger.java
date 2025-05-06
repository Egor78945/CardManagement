package com.example.card_management.service.security.token.aspect.logger;

import com.example.card_management.model.user.credential.dto.UserCredentialDTO;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.security.token.TokenService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TokenServiceLogger {
    private final Logger log;

    public TokenServiceLogger() {
        this.log = LoggerFactory.getLogger(TokenServiceLogger.class);
    }

    @Before("execution(String com.example.card_management.service.security.token.TokenService.generateToken(com.example.card_management.model.user.credential.entity.UserCredential)) && args(user) && target(implementation)")
    public void logBeforeGenerateToken(UserCredential user, TokenService<? extends UserCredential> implementation){
        log.info("\n{} : attempt to generate token for user\nUser : {}",implementation, user);
    }

    @AfterThrowing(value = "execution(String com.example.card_management.service.security.token.TokenService.generateToken(com.example.card_management.model.user.credential.entity.UserCredential)) && args(user) && target(implementation)", throwing = "exception")
    public void logAfterGenerateTokenThrowing(UserCredential user, TokenService<? extends UserCredential> implementation, Exception exception){
        log.error("\n{} : threw exception while generating token for user\nException : {}\nStack trace : {}\nMessage : {}\nUser : {}",implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), user);
    }

    @AfterReturning("execution(String com.example.card_management.service.security.token.TokenService.generateToken(com.example.card_management.model.user.credential.entity.UserCredential)) && args(user) && target(implementation)")
    public void logAfterGenerateToken(UserCredential user, TokenService<? extends UserCredential> implementation){
        log.info("\n{} : token has been received by user\nUser : {}",implementation, user);
    }
}
