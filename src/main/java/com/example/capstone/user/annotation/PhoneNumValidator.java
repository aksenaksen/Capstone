package com.example.capstone.user.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.example.capstone.user.constant.UserConstants.PHONENUMBER_PATTERN;

public class PhoneNumValidator implements ConstraintValidator<PhoneNum, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {

        if (!isValidPhoneNumber(phoneNumber)) {
            return false;
        }

        return true;
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {

        return phoneNumber.matches(PHONENUMBER_PATTERN);
    }
}