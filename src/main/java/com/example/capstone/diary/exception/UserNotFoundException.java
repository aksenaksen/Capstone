package com.example.capstone.diary.exception;

import com.example.capstone.dto.ErrorResponseDto;
import com.example.capstone.dto.ResponseCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{

    private final ErrorResponseDto errorResponseDto;
    public UserNotFoundException() {
        super(ResponseCode.USER_NOT_FOUND.getMessage());
        this.errorResponseDto = new ErrorResponseDto(
                ResponseCode.USER_NOT_FOUND.getHttpStatusCode(),
                ResponseCode.USER_NOT_FOUND.getMessage()
        );
    }}
