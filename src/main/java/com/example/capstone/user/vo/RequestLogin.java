package com.example.capstone.user.vo;


import com.example.capstone.user.annotation.Email;
import com.example.capstone.user.annotation.Password;
import lombok.Data;

@Data
public class RequestLogin {

    @Email
    private String email;
    @Password
    private String password;

}
