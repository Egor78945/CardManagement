package com.example.card_management.controller.security.authentication.advice;

import com.example.card_management.controller.security.authentication.advice.handler.AuthenticationControllerExceptionHandler;
import com.example.card_management.exception.AuthenticationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Tag(name = "AuthenticationControllerAdvice", description = "Обработчик ошибок, возникших в контроллере, помеченным AuthenticationControllerExceptionHandler")
@ControllerAdvice(annotations = AuthenticationControllerExceptionHandler.class)
public class AuthenticationControllerAdvice {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> authenticationExceptionHandler(AuthenticationException e){
        return new ResponseEntity<>(Map.of("authentication error", e.getMessage()), HttpStatusCode.valueOf(401));
    }
}
