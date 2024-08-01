package com.example.capstone.user.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
public @interface UserName {

    String message() default "아이디의 길이는 최소 5자 이상 최대 20자 이하, 특수문자가 포함되어서는 안됩니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}