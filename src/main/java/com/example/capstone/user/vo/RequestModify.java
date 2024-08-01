package com.example.capstone.user.vo;

import com.example.capstone.user.annotation.Password;
import com.example.capstone.user.annotation.PhoneNum;
import com.example.capstone.user.annotation.UserName;
import lombok.Data;

@Data
public class RequestModify {

    @Password
    private String password;

    @PhoneNum
    private String phoneNum;

    @UserName
    private String username;

}
