package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.utils.jwt.LoginUser;
import com.book.service.user.dto.response.ProfileResDto;
import com.book.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;


    @Auth
    @GetMapping("/user/profile")
    public ResponseEntity<ProfileResDto> userProfile(@LoginUser Long userId) {
        //User user = userDetail.getUser();
        ProfileResDto userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ProfileResDto> profile(@PathVariable Long id){
        ProfileResDto userProfile = userService.getProfile(id);
        return ResponseEntity.ok(userProfile);
    }

}
