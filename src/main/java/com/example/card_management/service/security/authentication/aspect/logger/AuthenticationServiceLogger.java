package com.example.card_management.service.security.authentication.aspect.logger;

import com.example.card_management.model.user.credential.dto.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationServiceLogger {
    private final Logger log;

    public AuthenticationServiceLogger() {
        this.log = LoggerFactory.getLogger(AuthenticationServiceLogger.class);
    }

    @Before("execution(void com.example.card_management.service.security.authentication.AuthenticationService.register(com.example.card_management.model.user.credential.dto.UserCredentialDTO)) && args(user) && target(implementation)")
    public void logBeforeRegisterUser(UserCredentialDTO user, AuthenticationService<? extends UserCredentialDTO> implementation) {
        log.info("\n{} : attempt to register user\nUser : {}", implementation, user);
    }

    @AfterThrowing(value = "execution(void com.example.card_management.service.security.authentication.AuthenticationService.register(com.example.card_management.model.user.credential.dto.UserCredentialDTO)) && args(user) && target(implementation)", throwing = "exception")
    public void logAfterRegisterUserThrowing(UserCredentialDTO user, AuthenticationService<? extends UserCredentialDTO> implementation, Exception exception) {
        log.error("\n{} : threw exception while registering an user\nException : {}\nStack trace : {}\nMessage : {}\nUser : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), user);
    }

    @AfterReturning("execution(void com.example.card_management.service.security.authentication.AuthenticationService.register(com.example.card_management.model.user.credential.dto.UserCredentialDTO)) && args(user) && target(implementation)")
    public void logAfterRegisterUser(UserCredentialDTO user, AuthenticationService<? extends UserCredentialDTO> implementation) {
        log.info("\n{} : user has been registered\nUser : {}", implementation, user);
    }

    @Before("execution(String com.example.card_management.service.security.authentication.AuthenticationService.authenticate(com.example.card_management.model.user.credential.dto.UserCredentialDTO)) && args(user) && target(implementation)")
    public void logBeforeAuthenticateUser(UserCredentialDTO user, AuthenticationService<? extends UserCredentialDTO> implementation) {
        log.info("\n{} : attempt to authenticate user\nUser : {}", implementation, user);
    }

    @AfterThrowing(value = "execution(String com.example.card_management.service.security.authentication.AuthenticationService.register(com.example.card_management.model.user.credential.dto.UserCredentialDTO)) && args(user) && target(implementation)", throwing = "exception")
    public void logAfterAuthenticateUserThrowing(UserCredentialDTO user, AuthenticationService<? extends UserCredentialDTO> implementation, Exception exception) {
        log.error("\n{} : threw exception while authenticating an user\nException : {}\nStack trace : {}\nMessage : {}\nUser : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), user);
    }

    @AfterReturning("execution(String com.example.card_management.service.security.authentication.AuthenticationService.register(com.example.card_management.model.user.credential.dto.UserCredentialDTO)) && args(user) && target(implementation)")
    public void logAfterAuthenticateUser(UserCredentialDTO user, AuthenticationService<? extends UserCredentialDTO> implementation) {
        log.info("\n{} : user has been authenticated\nUser : {}", implementation, user);
    }
}
