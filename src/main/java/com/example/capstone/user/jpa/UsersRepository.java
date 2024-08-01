package com.example.capstone.user.jpa;

import com.example.capstone.user.vo.RequestModify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository{
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByPhoneNum(String phoneNum);

    boolean existsByPhoneNum(String phoneNum);

    Optional<UserEntity> findByRefreshToken(String refreshToken);

    Long updateUser(Long userId, RequestModify userModify);
}
