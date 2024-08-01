package com.example.capstone.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<UserEntity, Long>, UsersRepository{
}
