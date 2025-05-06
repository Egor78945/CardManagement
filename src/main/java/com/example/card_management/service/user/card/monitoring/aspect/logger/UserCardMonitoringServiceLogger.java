package com.example.card_management.service.user.card.monitoring.aspect.logger;

import com.example.card_management.service.security.token.aspect.logger.TokenServiceLogger;
import com.example.card_management.service.user.card.monitoring.UserCardMonitoringService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserCardMonitoringServiceLogger {
    private final Logger log;

    public UserCardMonitoringServiceLogger() {
        this.log = LoggerFactory.getLogger(UserCardMonitoringServiceLogger.class);
    }

    @Before("execution(void com.example.card_management.service.user.card.monitoring.UserCardMonitoringService.updateExpired()) && target(implementation)")
    public void beforeUpdateExpired(UserCardMonitoringService implementation) {
        log.info("\n{} : attempt to update expired user cards", implementation);
    }

    @AfterThrowing(value = "execution(void com.example.card_management.service.user.card.monitoring.UserCardMonitoringService.updateExpired()) && target(implementation)", throwing = "exception")
    public void afterUpdateExpiredThrowing(UserCardMonitoringService implementation, Exception exception){
        log.error("\n{} : threw exception while updating expired user cards\nException : {}\nStack trace : {}\nMessage : {}", implementation, exception.getClass().getSimpleName(), exception.getStackTrace(), exception.getMessage());
    }

    @AfterReturning("execution(void com.example.card_management.service.user.card.monitoring.UserCardMonitoringService.updateExpired()) && target(implementation)")
    public void afterUpdateExpired(UserCardMonitoringService implementation) {
        log.info("\n{} : expired cards has been updated", implementation);
    }
}
