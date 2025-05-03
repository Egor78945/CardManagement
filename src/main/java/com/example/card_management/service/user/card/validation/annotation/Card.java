package com.example.card_management.service.user.card.validation.annotation;

import com.example.card_management.service.user.card.validation.validator.CardValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CardValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Card {
    String message() default "invalid car number format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
