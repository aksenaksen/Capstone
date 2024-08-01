package com.example.capstone.user.service;

import com.example.capstone.user.dto.UserDto;
import com.example.capstone.user.jpa.UserEntity;
import com.example.capstone.user.jpa.UserRepository;
import com.example.capstone.user.jpa.UsersRepository;
import com.example.capstone.user.vo.RequestModify;
import com.example.capstone.user.vo.RequestRegister;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository usersRepository;
    private ModelMapper modelMapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto register(RequestRegister requestRegister) {

        requestRegister.setPassword(passwordEncoder.encode(requestRegister.getPassword()));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity entity =modelMapper.map(requestRegister, UserEntity.class);

        usersRepository.save(entity);

        return modelMapper.map(entity, UserDto.class);
    }
    @Transactional
    @Override
    public void deleteUserRefreshToken(String email){

        usersRepository.findByEmail(email).ifPresent(
                user -> {
                    user.destroyRefreshToken();
                    usersRepository.save(user);
                }
        );
    }

    @Transactional
    @Override
    public UserDto getUserDtoByEmail(String email){

            return usersRepository.findByEmail(email)
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .orElse(null);

    }

    @Transactional
    public UserDto modifyUserDtoByUserIdAndEmail(String email , Long userId, RequestModify requestUser){

        if(!usersRepository.findById(userId).get().getEmail().equals(email)){
            throw new UsernameNotFoundException("Not matched user");
        }

        usersRepository.updateUser(userId,requestUser);

        return usersRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElse(null);
    }

}
