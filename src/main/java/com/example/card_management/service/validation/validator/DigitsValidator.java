package com.example.card_management.service.validation.validator;

import com.example.card_management.service.validation.annotation.Digits;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DigitsValidator  implements ConstraintValidator<Digits, String> {
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
