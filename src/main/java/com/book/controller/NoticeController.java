package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.config.security.jwt.LoginUser;
import com.book.domain.alarm.Notice;
import com.book.domain.alarm.dto.response.NoticeResDto;
import com.book.domain.user.User;
import com.book.service.NoticeService;
import com.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final UserService userService;
    private final NoticeService noticeService;

    @Auth
    @GetMapping
    public ResponseEntity<List<NoticeResDto>> getNotice(@LoginUser Long userId){
        User user = userService.findUser(userId);
        List<Notice> notices = noticeService.getNotice(user, false);
        List<NoticeResDto> noticeResponse = new ArrayList<>();
        for (Notice notice : notices) {
            noticeResponse.add(notice.toResDto());
        }
        noticeService.markAsRead(notices);
        return ResponseEntity.ok(noticeResponse);
    }

    @Auth
    @GetMapping("/old")
    public ResponseEntity<List<NoticeResDto>> getOldNotice(@LoginUser Long userId){
        User user = userService.findUser(userId);
        List<Notice> notices = noticeService.getNotice(user, true);
        List<NoticeResDto> noticeResponse = new ArrayList<>();
        for (Notice notice : notices) {
            noticeResponse.add(notice.toResDto());
        }
        return ResponseEntity.ok(noticeResponse);
    }

    @Auth
    @DeleteMapping
    public ResponseEntity<Void> deleteNotice(@LoginUser Long userId,
                                             @RequestParam("id") Long id) {
        User user = userService.findUser(userId);
        noticeService.deleteNotice(user, id);
        return ResponseEntity.ok().build();
    }
}
