package com.example.card_management.service.validation.annotation;

import com.example.card_management.service.validation.validator.DigitsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DigitsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Digits {
    String message() default "string contains not only digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
