package com.example.capstone.user.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.example.capstone.user.constant.UserConstants.EMAIL_PATTERN;

public class EmailValidator implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (!isValidEmail(email)) {
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String Email){

        return Email.matches(EMAIL_PATTERN);
    }

}