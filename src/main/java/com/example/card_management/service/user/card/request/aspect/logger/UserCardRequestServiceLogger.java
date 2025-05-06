package com.example.card_management.service.user.card.request.aspect.logger;

import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.service.user.card.request.UserCardRequestService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserCardRequestServiceLogger {
    private final Logger log;

    public UserCardRequestServiceLogger() {
        this.log = LoggerFactory.getLogger(UserCardRequestServiceLogger.class);
    }

    @Before("execution(void com.example.card_management.service.user.card.request.UserCardRequestService.send(com.example.card_management.model.user.card.request.entity.UserCardRequest)) && args(request) && target(implementation)")
    public void beforeSend(UserCardRequest request, UserCardRequestService<? extends UserCardRequest> implementation) {
        log.info("\n{} : attempt to send user card request\nUser card request : {}", implementation, request);
    }

    @AfterThrowing(value = "execution(void com.example.card_management.service.user.card.request.UserCardRequestService.send(com.example.card_management.model.user.card.request.entity.UserCardRequest)) && args(request) && target(implementation)", throwing = "exception")
    public void afterSendThrowing(UserCardRequest request, UserCardRequestService<? extends UserCardRequest> implementation, Exception exception) {
        log.error("\n{} : threw exception while sending user card request\nException : {}\nStack trace : {}\nMessage : {}\nUser card request : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), request);
    }

    @AfterReturning("execution(void com.example.card_management.service.user.card.request.UserCardRequestService.send(com.example.card_management.model.user.card.request.entity.UserCardRequest)) && args(request) && target(implementation)")
    public void afterSend(UserCardRequest request, UserCardRequestService<? extends UserCardRequest> implementation) {
        log.info("\n{} : user card request has been sent\nUser card request : {}", implementation, request);
    }

    @Before("execution(com.example.card_management.model.user.card.request.entity.UserCardRequest com.example.card_management.service.user.card.request.UserCardRequestService.getBySenderEmail(String)) && args(email) && target(implementation)")
    public void beforeGetBySenderEmail(String email, UserCardRequestService<? extends UserCardRequest> implementation) {
        log.info("\n{} : attempt to get user card request by sender email\nSender email : {}", implementation, email);
    }

    @AfterThrowing(value = "execution(com.example.card_management.model.user.card.request.entity.UserCardRequest com.example.card_management.service.user.card.request.UserCardRequestService.getBySenderEmail(String)) && args(email) && target(implementation)", throwing = "exception")
    public void afterGetBySenderEmailThrowing(String email, UserCardRequestService<? extends UserCardRequest> implementation, Exception exception) {
        log.error("\n{} : threw exception while getting user card request by sender email\nException : {}\nStack trace : {}\nMessage : {}\nSender email : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), email);
    }

    @AfterReturning(value = "execution(com.example.card_management.model.user.card.request.entity.UserCardRequest com.example.card_management.service.user.card.request.UserCardRequestService.getBySenderEmail(String)) && args(email) && target(implementation)", returning = "result")
    public void afterGetBySenderEmail(UserCardRequest result, String email, UserCardRequestService<? extends UserCardRequest> implementation) {
        log.info("\n{} : user card request by sender email has been received\nSender email : {}\nUser card request : {}", implementation, email, result);
    }

    @Before("execution(java.util.List<com.example.card_management.model.user.card.request.entity.UserCardRequest> com.example.card_management.service.user.card.request.UserCardRequestService.getAll(int)) && args(pageNumber) && target(implementation)")
    public void beforeGetAll(int pageNumber, UserCardRequestService<? extends UserCardRequest> implementation) {
        log.info("\n{} : attempt to get all user card requests by page\nPage : {}", implementation, pageNumber);
    }

    @AfterThrowing(value = "execution(java.util.List<com.example.card_management.model.user.card.request.entity.UserCardRequest> com.example.card_management.service.user.card.request.UserCardRequestService.getAll(int)) && args(pageNumber) && target(implementation)", throwing = "exception")
    public void afterGetAll(int pageNumber, UserCardRequestService<? extends UserCardRequest> implementation, Exception exception) {
        log.error("\n{} : threw exception while getting all card requests by page\nException : {}\nStack trace : {}\nMessage : {}\nPage : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage(), pageNumber);
    }

    @AfterReturning("execution(java.util.List<com.example.card_management.model.user.card.request.entity.UserCardRequest> com.example.card_management.service.user.card.request.UserCardRequestService.getAll(int)) && args(pageNumber) && target(implementation)")
    public void afterGetAll(int pageNumber, UserCardRequestService<? extends UserCardRequest> implementation) {
        log.info("\n{} : all user card requests by page number has been received\nPage : {}", implementation, pageNumber);
    }
}
