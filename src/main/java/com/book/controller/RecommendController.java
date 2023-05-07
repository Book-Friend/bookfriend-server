package com.book.controller;

import com.book.config.auth.PrincipalDetails;
import com.book.domain.recommend.Recommend;
import com.book.domain.recommend.dto.request.RecommendCreate;
import com.book.domain.recommend.dto.request.RecommendUpdate;
import com.book.domain.recommend.dto.response.RecommendResDto;
import com.book.domain.user.User;
import com.book.service.RecommendService;
import com.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<RecommendResDto> getRecommend(@PathVariable Long id){
        Recommend recommend = recommendService.getRecommend(id);
        return ResponseEntity.ok(recommendService.getRecommendRes(recommend));
    }


    @PostMapping("/create")
    public ResponseEntity<Void> createRecommend(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                @RequestBody RecommendCreate recommend){
        User user = userService.getUserInfo(userDetails.getUsername());
        recommendService.createRecommend(user, recommend);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateRecommend(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                @PathVariable Long id,
                                                @RequestBody RecommendUpdate update){
        Recommend recommend = recommendService.checkAccessPermission(id, userDetails.getUsername());
        recommendService.updateRecommend(update, recommend);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRecommend(@AuthenticationPrincipal PrincipalDetails userDetails,
                                                @RequestParam Long id){
        recommendService.deleteRecommend(id);
        return ResponseEntity.ok().build();
    }
}
