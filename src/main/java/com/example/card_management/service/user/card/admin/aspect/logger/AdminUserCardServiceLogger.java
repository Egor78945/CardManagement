package com.example.card_management.service.user.card.admin.aspect.logger;

import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.service.security.token.aspect.logger.TokenServiceLogger;
import com.example.card_management.service.user.card.admin.AdminUserCardService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminUserCardServiceLogger {
    private final Logger log;

    public AdminUserCardServiceLogger() {
        this.log = LoggerFactory.getLogger(AdminUserCardServiceLogger.class);
    }

    @Before("execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.changeUserCardStatusByNumber(String, com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration)) && args(cardNumber, cardType) && target(implementation)")
    public void logBeforeChangeUserCardStatusByNumber(String cardNumber, UserCardTypeEnumeration cardType, AdminUserCardService<? extends UserCard> implementation) {
        log.info("\n{} : attempt to change user card status by card number\nCard number : {}\nCard type : {}", implementation, String.format("**** **** **** %s", cardNumber.substring(12)), cardType);
    }

    @AfterThrowing(value = "execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.changeUserCardStatusByNumber(String, com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration)) && args(cardNumber, cardType) && target(implementation)", throwing = "exception")
    public void logAfterGenerateTokenThrowing(String cardNumber, UserCardTypeEnumeration cardType, AdminUserCardService<? extends UserCard> implementation, Exception exception) {
        log.error("\n{} : threw exception while changing user card status\nException : {}\nStack trace : {}\nMessage : {}\nCard number : {}\nCard type : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), String.format("**** **** **** %s", cardNumber.substring(12)), cardType);
    }

    @AfterReturning("execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.changeUserCardStatusByNumber(String, com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration)) && args(cardNumber, cardType) && target(implementation)")
    public void logAfterGenerateToken(String cardNumber, UserCardTypeEnumeration cardType, AdminUserCardService<? extends UserCard> implementation) {
        log.info("\n{} : user card status by card number has been changed\nCard number : {}\nCard type : {}", implementation, String.format("**** **** **** %s", cardNumber.substring(12)), cardType);
    }

    @Before("execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.deleteCardByNumber(String)) && args(cardNumber) && target(implementation)")
    public void logBeforeDeleteUserCardByNumber(String cardNumber, AdminUserCardService<? extends UserCard> implementation) {
        log.info("\n{} : attempt to delete user card by card number\nCard number : {}", implementation, String.format("**** **** **** %s", cardNumber.substring(12)));
    }

    @AfterThrowing(value = "execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.deleteCardByNumber(String)) && args(cardNumber) && target(implementation)", throwing = "exception")
    public void logAfterDeleteUserCardByNumberThrowing(String cardNumber, AdminUserCardService<? extends UserCard> implementation, Exception exception) {
        log.error("\n{} : threw exception while deleting user card by card number\nException : {}\nStack trace : {}\nMessage : {}\nCard number : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), String.format("**** **** **** %s", cardNumber.substring(12)));
    }

    @AfterReturning("execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.deleteCardByNumber(String)) && args(cardNumber) && target(implementation)")
    public void logAfterDeleteUserCardByNumber(String cardNumber, AdminUserCardService<? extends UserCard> implementation) {
        log.info("\n{} : user card has been deleted by card number\nCard number : {}", implementation, String.format("**** **** **** %s", cardNumber.substring(12)));
    }

    @Before("execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.updateExpiredUserCards()) && target(implementation)")
    public void logBeforeUpdateExpiredUserCards(AdminUserCardService<? extends UserCard> implementation) {
        log.info("\n{} : attempt to update expired user cards", implementation);
    }

    @AfterThrowing(value = "execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.updateExpiredUserCards()) && target(implementation)", throwing = "exception")
    public void logAfterUpdateExpiredUserCardsThrowing(AdminUserCardService<? extends UserCard> implementation, Exception exception) {
        log.error("\n{} : threw exception while updating expired user cards\nException : {}\nStack trace : {}\nMessage : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage());
    }

    @AfterReturning("execution(void com.example.card_management.service.user.card.admin.AdminUserCardService.updateExpiredUserCards()) && target(implementation)")
    public void logAfterUpdateExpiredUserCards(AdminUserCardService<? extends UserCard> implementation) {
        log.info("\n{} : expired cards has been updated", implementation);
    }
}
