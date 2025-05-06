package com.example.card_management.service.user.credential.aspect.logger;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.service.user.credential.UserCredentialService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserCredentialServiceLogger {
    private final Logger log;

    public UserCredentialServiceLogger() {
        this.log = LoggerFactory.getLogger(UserCredentialServiceLogger.class);
    }

    @Before("execution(void com.example.card_management.service.user.credential.UserCredentialService.save(com.example.card_management.model.user.credential.entity.UserCredential)) && args(request) && target(implementation)")
    public void beforeSave(UserCredential request, UserCredentialService<? extends UserCredential> implementation) {
        log.info("\n{} : attempt to save user credential\nUser credential : {}", implementation, request);
    }

    @AfterThrowing(value = "execution(void com.example.card_management.service.user.credential.UserCredentialService.save(com.example.card_management.model.user.credential.entity.UserCredential)) && args(request) && target(implementation)", throwing = "exception")
    public void afterSaveThrowing(UserCredential request, UserCredentialService<? extends UserCredential> implementation, Exception exception) {
        log.error("\n{} : threw exception while saving user card\nException : {}\nStack trace : {}\nMessage : {}\nUser credential : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), request);
    }

    @AfterReturning("execution(void com.example.card_management.service.user.credential.UserCredentialService.save(com.example.card_management.model.user.credential.entity.UserCredential)) && args(request) && target(implementation)")
    public void afterSave(UserCredential request, UserCredentialService<? extends UserCredential> implementation) {
        log.info("\n{} : user credential has been saved\nUser card : {}", implementation, request);
    }

    @Before("execution(com.example.card_management.model.user.credential.entity.UserCredential com.example.card_management.service.user.credential.UserCredentialService.getByEmail(String)) && args(email) && target(implementation)")
    public void beforeGetByEmail(String email, UserCredentialService<? extends UserCredential> implementation) {
        log.info("\n{} : attempt to get user credential by email\nUser email : {}", implementation, email);
    }

    @AfterThrowing(value = "execution(com.example.card_management.model.user.credential.entity.UserCredential com.example.card_management.service.user.credential.UserCredentialService.getByEmail(String)) && args(email) && target(implementation)", throwing = "exception")
    public void afterGetByEmailThrowing(String email, UserCredentialService<? extends UserCredential> implementation, Exception exception) {
        log.error("\n{} : threw exception while getting user credential by email\nException : {}\nStack trace : {}\nMessage : {}\nUser email : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), email);
    }

    @AfterReturning("execution(com.example.card_management.model.user.credential.entity.UserCredential com.example.card_management.service.user.credential.UserCredentialService.getByEmail(String)) && args(email) && target(implementation)")
    public void afterGetByEmail(String email, UserCredentialService<? extends UserCredential> implementation) {
        log.info("\n{} : user credential has been received\nUser email : {}", implementation, email);
    }
}
