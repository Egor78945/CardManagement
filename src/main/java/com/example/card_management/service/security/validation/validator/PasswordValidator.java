package com.example.card_management.service.security.validation.validator;

import com.example.card_management.service.security.validation.annotation.Password;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Tag(name = "PasswordValidator", description = "Валидатор, проверяющий правильную структуру пароля пользователя")
public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank()) {
            return false;
        }
        int digitsCount = 0;
        int lettersCount = 0;
        for (char c : password.toLowerCase().toCharArray()) {
            if (c >= 97 && c <= 122 || c >= 65 && c <= 90) {
                lettersCount++;
            } else if (c >= 48 && c <= 57) {
                digitsCount++;
            } else {
                return false;
            }
        }
        return digitsCount >= 5 && lettersCount >= 5;
    }
}
