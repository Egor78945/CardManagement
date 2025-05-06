package com.example.card_management.controller.transaction.advice;

import com.example.card_management.controller.transaction.advice.handler.TransactionControllerExceptionHandler;
import com.example.card_management.exception.TransactionManagementException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Tag(name = "TransactionControllerAdvice", description = "Обработчик ошибок, возникших в контроллере, помеченным TransactionControllerExceptionHandler")
@ControllerAdvice(annotations = TransactionControllerExceptionHandler.class)
public class TransactionControllerAdvice {
    @ExceptionHandler(TransactionManagementException.class)
    public ResponseEntity<Map<String, Object>> transactionManagementExceptionHandler(TransactionManagementException e) {
        return new ResponseEntity<>(Map.of("transaction error", e.getMessage()), HttpStatusCode.valueOf(400));
    }
}
