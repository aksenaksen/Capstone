package com.example.capstone.user.controller;

import com.example.capstone.user.dto.UserDto;
import com.example.capstone.user.security.JwtService;
import com.example.capstone.user.service.UserService;
import com.example.capstone.user.vo.RequestModify;
import com.example.capstone.user.vo.RequestRegister;
import com.example.capstone.user.vo.ResponseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User API")
@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/info")
    public ResponseEntity<String> success(){
        return ResponseEntity.ok("good");
    }

    @Operation(summary = "로그아웃", description = "사용자 로그아웃")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        String email = getEmailByRequest(request);
        userService.deleteUserRefreshToken(email);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }


    @Operation(summary = "사용자 정보 수정", description = "사용자 정보 수정")
    @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> modifyUser(@PathVariable("userId") Long userId, @RequestBody @Valid RequestModify requestUser, HttpServletRequest request) {

        String email = getEmailByRequest(request);
        log.info(email);

        UserDto user = userService.modifyUserDtoByUserIdAndEmail(email,userId,requestUser);

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "회원가입", description = "회원가입 관련")
    @ApiResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping("/register")
    public ResponseEntity<ResponseUser> registerUser(@RequestBody @Valid RequestRegister requestRegister) {

        UserDto userDto = userService.register(requestRegister);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ResponseUser responseUser = modelMapper.map(userDto,ResponseUser.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseUser);
    }

    @Operation(summary = "현재 사용자 조회 jwt를 이용", description = "현재 세션을 이용해 사용자 정보 조회")
    @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공")
    @GetMapping("/session")
    public ResponseEntity<ResponseUser> findUserBySession(HttpServletRequest request) {

        String email = getEmailByRequest(request);
        ResponseUser user = modelMapper.map(userService.getUserDtoByEmail(email), ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    private String getEmailByRequest(HttpServletRequest request){
        return jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .flatMap(jwtService::extractEmail)
                .orElse(null);
    }
}
