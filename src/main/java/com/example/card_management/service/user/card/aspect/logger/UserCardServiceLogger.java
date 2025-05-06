package com.example.card_management.service.user.card.aspect.logger;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.service.user.card.transaction.UserCardTransactionService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserCardServiceLogger {
    private final Logger log;

    public UserCardServiceLogger() {
        this.log = LoggerFactory.getLogger(UserCardServiceLogger.class);
    }

    @Before("execution(String com.example.card_management.service.user.card.UserCardService.save(com.example.card_management.model.user.card.entity.UserCard)) && args(request) && target(implementation)")
    public void beforeSave(UserCard request, UserCardService<? extends UserCard> implementation) {
        log.info("\n{} : attempt to save user card\nUser card : {}", implementation, request);
    }

    @AfterThrowing(value = "execution(String com.example.card_management.service.user.card.UserCardService.save(com.example.card_management.model.user.card.entity.UserCard)) && args(request) && target(implementation)", throwing = "exception")
    public void afterSaveThrowing(UserCard request, UserCardService<? extends UserCard> implementation, Exception exception) {
        log.error("\n{} : threw exception while saving user card\nException : {}\nStack trace : {}\nMessage : {}\nUser card : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), request);
    }

    @AfterReturning("execution(String com.example.card_management.service.user.card.UserCardService.save(com.example.card_management.model.user.card.entity.UserCard)) && args(request) && target(implementation)")
    public void afterSave(UserCard request, UserCardService<? extends UserCard> implementation) {
        log.info("\n{} : user card has been saved\nUser card : {}", implementation, request);
    }
}
