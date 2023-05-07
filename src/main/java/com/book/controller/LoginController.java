package com.book.controller;

import com.book.config.security.jwt.JwtResponse;
import com.book.config.security.jwt.JwtTokenProvider;
import com.book.domain.user.dto.request.LoginDto;
import com.book.domain.user.dto.request.UserCreateDto;
import com.book.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
     private final JwtTokenProvider jwtTokenProvider;

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

    @PostMapping("/login")
    public ResponseEntity<Void> login1(@RequestBody LoginDto loginDto) {
        String token = jwtTokenProvider.createToken(loginDto.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ token);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
