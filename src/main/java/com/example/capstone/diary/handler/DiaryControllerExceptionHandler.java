package com.example.capstone.diary.handler;

import com.example.capstone.diary.controller.DiaryController;
import com.example.capstone.diary.exception.UserNotFoundException;
import com.example.capstone.dto.ErrorResponseDto;
import com.example.capstone.dto.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice(assignableTypes = DiaryController.class)

public class DiaryControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFoundExceptionHandler(UserNotFoundException ex){

        log.error("Error Location: ", ex);
        log.error("Error Msg: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getErrorResponseDto());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {

        log.error("Error Location: ", ex);
        log.error("Error Msg: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ResponseCode.DIARY_PARAM_NOT_ALLOWED_FORMAT.getCode(),
                ResponseCode.DIARY_PARAM_NOT_ALLOWED_FORMAT.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

        String msg=ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        log.error("Error Location: ", ex);
        log.error("Error Msg: {}", msg);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(ResponseCode.DIARY_REQUEST_NOT_ALLOWED_FORMAT.getCode(), msg));
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalServerErrorExceptionHandler(Exception ex){

        log.error("Error Location: {}", ex.getStackTrace()[0]);
        log.error("Error Msg: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
