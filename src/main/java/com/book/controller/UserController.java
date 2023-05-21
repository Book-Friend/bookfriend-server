package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.utils.jwt.LoginUser;
import com.book.domain.user.User;
import com.book.service.user.dto.request.PasswordChangeDto;
import com.book.service.user.dto.request.UserUpdateDto;
import com.book.utils.ImageService;
import com.book.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @Auth
    @PostMapping("/password")
    public ResponseEntity<Void> changePassword(@LoginUser Long userId, @RequestBody PasswordChangeDto password) {
        userService.updatePassword(userId, password);

        return ResponseEntity.ok().build();
    }

    @Auth
    @PostMapping("/update")
    public ResponseEntity<Void> changeName(@LoginUser Long userId, @RequestBody UserUpdateDto updateDto) {
        userService.updateProfile(userId, updateDto);

        return ResponseEntity.ok().build();
    }

    @Auth
    @PostMapping("/update/image")
    public ResponseEntity<Void> changeImage(@LoginUser Long userId, @RequestPart MultipartFile multipartFile) {
        User user = userService.findUser(userId);
        imageService.updateImage(user, multipartFile);

        return ResponseEntity.ok().build();
    }

    @Auth
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> deleteUser(@LoginUser Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok().build();
    }

}