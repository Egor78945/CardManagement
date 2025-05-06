package com.example.card_management.service.user.card.transaction.aspect.logger;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO;
import com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO;
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
public class UserCardTransactionServiceLogger {
    private final Logger log;

    public UserCardTransactionServiceLogger() {
        this.log = LoggerFactory.getLogger(UserCardTransactionServiceLogger.class);
    }

    @Before("execution(double com.example.card_management.service.user.card.transaction.UserCardTransactionService.insert(com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO)) && args(request) && target(implementation)")
    public void beforeInsert(TransactionInsertDTO request, UserCardTransactionService<? extends UserCard> implementation) {
        log.info("\n{} : attempt to make insert transaction\nRequest : {}", implementation, request);
    }

    @AfterThrowing(value = "execution(double com.example.card_management.service.user.card.transaction.UserCardTransactionService.insert(com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO)) && args(request) && target(implementation)", throwing = "exception")
    public void afterInsertThrowing(TransactionInsertDTO request, UserCardTransactionService<? extends UserCard> implementation, Exception exception) {
        log.error("\n{} : threw exception while making insert transaction\nException : {}\nStack trace : {}\nMessage : {}\nRequest : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), request);
    }

    @AfterReturning("execution(double com.example.card_management.service.user.card.transaction.UserCardTransactionService.insert(com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO)) && args(request) && target(implementation)")
    public void afterInsert(TransactionInsertDTO request, UserCardTransactionService<? extends UserCard> implementation) {
        log.info("\n{} : insert transaction request has been made\nRequest : {}", implementation, request);
    }

    @Before("execution(double com.example.card_management.service.user.card.transaction.UserCardTransactionService.transfer(com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO)) && args(request) && target(implementation)")
    public void beforeTransfer(TransactionTransferDTO request, UserCardTransactionService<? extends UserCard> implementation) {
        log.info("\n{} : attempt to make transfer transaction\nRequest : {}", implementation, request);
    }

    @AfterThrowing(value = "execution(double com.example.card_management.service.user.card.transaction.UserCardTransactionService.transfer(com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO)) && args(request) && target(implementation)", throwing = "exception")
    public void afterTransferThrowing(TransactionTransferDTO request, UserCardTransactionService<? extends UserCard> implementation, Exception exception) {
        log.error("\n{} : threw exception while making transfer transaction\nException : {}\nStack trace : {}\nMessage : {}\nRequest : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), request);
    }

    @AfterReturning("execution(double com.example.card_management.service.user.card.transaction.UserCardTransactionService.transfer(com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO)) && args(request) && target(implementation)")
    public void afterTransfer(TransactionTransferDTO request, UserCardTransactionService<? extends UserCard> implementation) {
        log.info("\n{} : transfer transaction request has been made\nRequest : {}", implementation, request);
    }
}
