package com.example.capstone.user.service;

import com.example.capstone.user.dto.UserDto;
import com.example.capstone.user.vo.RequestModify;
import com.example.capstone.user.vo.RequestRegister;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    public UserDto register(RequestRegister requestRegister);

    @Transactional
    void deleteUserRefreshToken(String email);

    UserDto getUserDtoByEmail(String email);

    UserDto modifyUserDtoByUserIdAndEmail(String email , Long userId, RequestModify requestUser);
}
