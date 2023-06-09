package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.utils.jwt.LoginUser;
import com.book.domain.user.User;
import com.book.service.follow.FollowService;
import com.book.service.notice.NoticeService;
import com.book.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;
    private final NoticeService noticeService;

    @Auth
    @PostMapping("/create")
    public ResponseEntity<Void> follow(@LoginUser Long userId, @RequestParam Long id){
        User follower = userService.findUser(id);
        User following = userService.findUser(userId);
        followService.follow(following, follower);
        noticeService.addNotice(follower, following.getName() + "님이 팔로우 합니다.");
        return ResponseEntity.ok().build();
    }

    @Auth
    @DeleteMapping("/delete")
    public ResponseEntity<Void> cancel(@LoginUser Long userId, @RequestParam Long id){
        User follower = userService.findUser(id);
        User following = userService.findUser(userId);
        followService.followCancel(following, follower);

        return ResponseEntity.ok().build();
    }
}
