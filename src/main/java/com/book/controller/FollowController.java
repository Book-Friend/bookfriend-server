package com.book.controller;

import com.book.config.auth.PrincipalDetails;
import com.book.domain.user.User;
import com.book.service.FollowService;
import com.book.service.NoticeService;
import com.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserService userService;
    private final NoticeService noticeService;

    @PostMapping("/create")
    public ResponseEntity<Void> follow(@AuthenticationPrincipal PrincipalDetails userDetails,
                                       @RequestParam Long id){
        User follower = userService.findUser(id);
        User following = userService.findUser(userDetails.getUser().getId());
        followService.follow(following, follower);
        noticeService.addNotice(follower, following.getName() + "님이 팔로우 합니다.");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> cancel(@AuthenticationPrincipal PrincipalDetails userDetails,
                                       @RequestParam Long id){
        User follower = userService.findUser(id);
        User following = userService.findUser(userDetails.getUser().getId());
        followService.followCancel(following, follower);

        return ResponseEntity.ok().build();
    }
}
