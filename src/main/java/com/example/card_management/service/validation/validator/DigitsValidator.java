package com.example.card_management.service.validation.validator;

import com.example.card_management.service.validation.annotation.Digits;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Tag(name = "DigitsValidator", description = "Валидатор, проверяющий, состоит ли строка только из цифр")
public class DigitsValidator implements ConstraintValidator<Digits, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for(char i : s.toCharArray()){
            if(i < 48 || i > 57){
                return false;
            }
        }
        return true;
    }
}
