package com.book.controller;

import com.book.config.auth.PrincipalDetails;
import com.book.domain.user.User;
import com.book.domain.user.dto.request.PasswordChangeDto;
import com.book.domain.user.dto.request.UserUpdateDto;
import com.book.service.ImageService;
import com.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    @PostMapping("/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal PrincipalDetails userDetails,
                                               @RequestBody PasswordChangeDto password) {
        userService.updatePassword(userDetails.getUser().getId(), password);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> changeName(@AuthenticationPrincipal PrincipalDetails userDetails,
                                           @RequestBody UserUpdateDto updateDto) {
        userService.updateProfile(userDetails.getUser().getId(), updateDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/image")
    public ResponseEntity<Void> changeImage(@AuthenticationPrincipal PrincipalDetails userDetails,
                                            @RequestPart MultipartFile multipartFile) {
        User user = userService.findUser(userDetails.getUser().getId());
        imageService.updateImage(user, multipartFile);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal PrincipalDetails userDetails) {
        userService.deleteUser(userDetails.getUser());

        return ResponseEntity.ok().build();
    }

}