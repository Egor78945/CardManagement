package com.example.card_management.service.user.card.validation.validator;

import com.example.card_management.service.user.card.validation.annotation.Card;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardValidator implements ConstraintValidator<Card, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.length() != 16 || (s.charAt(0) != '4' && s.charAt(0) != '5')) {
            return false;
        }
        for (char i : s.toCharArray()) {
            if (i < 48 || i > 57) {
                return false;
            }
        }
        return true;
    }
}
