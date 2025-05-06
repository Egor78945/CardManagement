package com.example.card_management.controller.card.advice;

import com.example.card_management.controller.card.advice.handler.CardControllerExceptionHandler;
import com.example.card_management.exception.CardManagementException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Tag(name = "CardControllerAdvice", description = "Обработчик ошибок, возникших в контроллере, помеченным CardControllerExceptionHandler")

@ControllerAdvice(annotations = CardControllerExceptionHandler.class)
public class CardControllerAdvice {
    @ExceptionHandler(CardManagementException.class)
    public ResponseEntity<Map<String, String>> cardManagementExceptionHandler(CardManagementException e) {
        return new ResponseEntity<>(Map.of("card error", e.getMessage()), HttpStatusCode.valueOf(400));
    }
}
