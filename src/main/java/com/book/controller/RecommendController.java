package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.utils.jwt.LoginUser;
import com.book.domain.recommend.Recommend;
import com.book.service.recommend.dto.request.RecommendCreate;
import com.book.service.recommend.dto.request.RecommendUpdate;
import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.domain.user.User;
import com.book.service.recommend.RecommendService;
import com.book.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Auth
    @PostMapping("/create")
    public ResponseEntity<Void> createRecommend(@LoginUser Long userId, @RequestBody RecommendCreate recommend){
        User user = userService.findUser(userId);
        recommendService.createRecommend(user, recommend);
        return ResponseEntity.ok().build();
    }

    @Auth
    @PostMapping("/update/{id}")
    public ResponseEntity<Void> updateRecommend(@LoginUser Long userId,
                                                @PathVariable Long id,
                                                @RequestBody RecommendUpdate update){
        Recommend recommend = recommendService.checkAccessPermission(id, userId);
        recommendService.updateRecommend(update, recommend);
        return ResponseEntity.ok().build();
    }

    @Auth
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRecommend(@LoginUser Long userId, @RequestParam Long id){
        Recommend recommend = recommendService.checkAccessPermission(id, userId);
        recommendService.deleteRecommend(id);
        return ResponseEntity.ok().build();
    }
}
