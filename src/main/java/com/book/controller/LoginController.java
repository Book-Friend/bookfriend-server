package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.utils.jwt.JwtResponse;
import com.book.utils.jwt.JwtTokenProvider;
import com.book.utils.jwt.LoginUser;
import com.book.utils.jwt.RefreshTokenReq;
import com.book.service.user.dto.request.LoginDto;
import com.book.service.user.dto.request.UserCreateDto;
import com.book.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

     private final UserService userService;
     private final JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(
            @Valid @RequestBody UserCreateDto userCreateDto) {
        userService.signUp(userCreateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<LoginDto> login(){
        return ResponseEntity.ok().body(new LoginDto());
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginProcess(@RequestBody LoginDto loginDto) {

        Long userId = userService.login(loginDto);
        String accessToken = tokenProvider.createAccessToken(userId);
        String refreshToken = tokenProvider.createRefreshToken(userId);

        return new ResponseEntity<>(new JwtResponse(accessToken, refreshToken), HttpStatus.OK);
    }

    //로그아웃
    @Auth
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser Long userId){
        userService.logout(userId);
        return ResponseEntity.ok().build();
    }

    //엑세스 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshTokenReq tokenReq){
        JwtResponse jwtResponse = tokenProvider.reissueToken(tokenReq.getRefreshToken());
        return ResponseEntity.ok().body(jwtResponse);
    }
}
