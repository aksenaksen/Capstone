package com.example.capstone.user.vo;


import com.example.capstone.user.annotation.Email;
import com.example.capstone.user.annotation.Password;
import com.example.capstone.user.annotation.PhoneNum;
import com.example.capstone.user.annotation.UserName;
import lombok.Data;

@Data
public class RequestRegister {

    @UserName
    private String username;
    @Password
    private String password;
    @PhoneNum
    private String phoneNum;
    @Email
    private String email;
}
