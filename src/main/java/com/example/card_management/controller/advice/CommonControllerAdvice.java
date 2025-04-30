package com.example.card_management.controller.advice;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(annotations = CommonControllerExceptionHandler.class)
public class CommonControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> runtimeExceptionHandler(RuntimeException runtimeException) {
        return new ResponseEntity<>(Map.of("unknown error", runtimeException.getMessage()), HttpStatusCode.valueOf(505));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Map<String, String>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        var map = new HashMap<String, String>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(Map.of("validation error", map), HttpStatusCode.valueOf(400));
    }
}
