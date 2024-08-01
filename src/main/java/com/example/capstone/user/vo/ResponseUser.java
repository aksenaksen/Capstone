package com.example.capstone.user.vo;

import com.example.capstone.user.annotation.Password;
import com.example.capstone.user.annotation.PhoneNum;
import com.example.capstone.user.annotation.UserName;
import lombok.Data;

@Data
public class ResponseUser {

    private Long id;
    private String username;
    private String email;
    private String phoneNum;
}
