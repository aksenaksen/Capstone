package com.example.capstone.user.dto;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN(0, "ADMIN"),
    ROLE_USER(1, "USER");

    private final int code;
    private final String role;

    UserRole(int code, String role){
        this.code = code;
        this.role = role;
    }
}