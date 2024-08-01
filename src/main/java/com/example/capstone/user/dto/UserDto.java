package com.example.capstone.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String phoneNum;

    private String username;

    private String refreshToken;

}
