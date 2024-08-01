package com.example.capstone.user.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.example.capstone.user.constant.UserConstants.USERNAME_PATTERN;

public class UserNameValidator implements ConstraintValidator<UserName, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (!isValidUserName(username)) {
            return false;
        }
        return true;
    }

    private boolean isValidUserName(String username) {

        return username.matches(USERNAME_PATTERN);
    }
}