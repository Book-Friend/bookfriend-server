package com.book.controller;

import com.book.config.auth.PrincipalDetails;
import com.book.domain.alarm.Notice;
import com.book.domain.alarm.dto.response.NoticeResDto;
import com.book.service.NoticeService;
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

    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<List<NoticeResDto>> getNotice(@AuthenticationPrincipal PrincipalDetails userDetails){
        List<Notice> notices = noticeService.getNotice(userDetails.getUser(), false);
        List<NoticeResDto> noticeResponse = new ArrayList<>();
        for (Notice notice : notices) {
            noticeResponse.add(notice.toResDto());
        }
        noticeService.markAsRead(notices);
        return ResponseEntity.ok(noticeResponse);
    }

    @GetMapping("/old")
    public ResponseEntity<List<NoticeResDto>> getOldNotice(@AuthenticationPrincipal PrincipalDetails userDetails){
        List<Notice> notices = noticeService.getNotice(userDetails.getUser(), true);
        List<NoticeResDto> noticeResponse = new ArrayList<>();
        for (Notice notice : notices) {
            noticeResponse.add(notice.toResDto());
        }
        return ResponseEntity.ok(noticeResponse);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteNotice(@AuthenticationPrincipal PrincipalDetails userDetails,
                                             @RequestParam("id") Long id) {
        noticeService.deleteNotice(userDetails.getUser(), id);
        return ResponseEntity.ok().build();
    }
}
