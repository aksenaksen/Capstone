package com.example.capstone.user.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.example.capstone.user.constant.UserConstants.PASSWORD_PATTERN;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (!isValidPassword(password)) {
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {

        return password.matches(PASSWORD_PATTERN);
    }
}