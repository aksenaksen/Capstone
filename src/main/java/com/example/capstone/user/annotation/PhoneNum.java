package com.example.capstone.user.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumValidator.class)
public @interface PhoneNum {

    String message() default "전화번호 형식이 일치하지않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}