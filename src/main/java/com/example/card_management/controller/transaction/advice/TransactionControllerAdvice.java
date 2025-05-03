package com.example.card_management.controller.transaction.advice;

import com.example.card_management.controller.transaction.advice.handler.TransactionControllerExceptionHandler;
import com.example.card_management.exception.TransactionManagementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice(annotations = TransactionControllerExceptionHandler.class)
public class TransactionControllerAdvice {
    @ExceptionHandler(TransactionManagementException.class)
    public ResponseEntity<Map<String, Object>> transactionManagementExceptionHandler(TransactionManagementException e) {
        return ResponseEntity.ok(Map.of("transaction error", e.getMessage()));
    }
}
