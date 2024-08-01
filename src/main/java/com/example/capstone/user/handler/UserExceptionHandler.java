package com.example.capstone.user.handler;

import com.example.capstone.user.controller.UserController;
import com.example.capstone.user.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.capstone.user.constant.CustomStatusCode.*;
import static com.example.capstone.user.constant.UserConstants.*;


@Slf4j
@RestControllerAdvice(assignableTypes = {UserController.class})
public class UserExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception) {

        log.error("Exception [Err_Location] : {}", exception.getStackTrace()[0]);
        log.error("Exception [Err_Msg] : {}",   exception.getMessage());

        if (exception.getBindingResult().getAllErrors().get(0).getDefaultMessage().equals(NOT_ALLOWED_EMAIL_FORM_LITERAL)) {

            ErrorResponseDto errorResponseDto = new ErrorResponseDto(NOT_ALLOWED_EMAIL_FORM, NOT_ALLOWED_EMAIL_FORM_LITERAL);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseDto);
        }

        if (exception.getBindingResult().getAllErrors().get(0).getDefaultMessage().equals(NOT_ALLOWED_PASSWORD_FORM_LITERAL)) {

            ErrorResponseDto errorResponseDto = new ErrorResponseDto(NOT_ALLOWED_PASSWORD_FORM, NOT_ALLOWED_PASSWORD_FORM_LITERAL);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseDto);
        }
        if (exception.getBindingResult().getAllErrors().get(0).getDefaultMessage().equals(NOT_ALLOWED_USERNAME_FORM_LITERAL)) {

            ErrorResponseDto errorResponseDto = new ErrorResponseDto(NOT_ALLOWED_USERNAME_FORM, NOT_ALLOWED_USERNAME_FORM_LITERAL);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseDto);
        }
        if (exception.getBindingResult().getAllErrors().get(0).getDefaultMessage().equals(NOT_ALLOWED_PHONENUMBER_FORM_LITERAL )) {

            ErrorResponseDto errorResponseDto = new ErrorResponseDto(NOT_ALLOWED_PHONENUMBER_FORM, NOT_ALLOWED_PHONENUMBER_FORM_LITERAL );

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseDto);
        }


        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), NOT_ALLOWED_BODY);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }


}
