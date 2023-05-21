package com.book.controller;

import com.book.domain.recommend.Recommend;
import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.service.recommend.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final RecommendService recommendService;

    @GetMapping("/home/{id}")
    public ResponseEntity<List<RecommendResDto>> getRecommend(@PathVariable Long id){
        List<Recommend> recommendList = recommendService.getRecommendList(id);
        return ResponseEntity.ok(recommendList.stream().map(r -> recommendService.getRecommendRes(r)).collect(Collectors.toList()));
    }

}
