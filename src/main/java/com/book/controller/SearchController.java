package com.book.controller;

import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.service.recommend.dto.response.RecommendSearchDto;
import com.book.service.user.dto.response.UserSearchDto;
import com.book.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

//    @GetMapping("/recommend")
//    public ResponseEntity<List<RecommendSearchDto>> searchRecommend(@RequestParam(value = "keyword") String title,
//                                                                  @PageableDefault Pageable pageable){
//        List<RecommendSearchDto> search = searchService.searchRecommend(title, pageable);
//        return ResponseEntity.ok().body(search);
//    }

    @GetMapping("/recommend")
    public ResponseEntity<Page<RecommendSearchDto>> searchRecommendList(@RequestParam(value = "keyword") String keyword,
                                                                        @PageableDefault Pageable pageable){
        Page<RecommendSearchDto> search = searchService.searchRecommendList(keyword, pageable);
        return ResponseEntity.ok().body(search);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<UserSearchDto>> searchUser(@RequestParam("keyword") String email,
                                                          @PageableDefault Pageable pageable){
        Page<UserSearchDto> userSearchDtos = searchService.searchUser(email, pageable);
        return ResponseEntity.ok().body(userSearchDtos);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<RecommendResDto>> searchTag(@RequestParam("keyword") String tag,
                                                           @PageableDefault Pageable pageable){
        List<RecommendResDto> search = searchService.searchRecommendByTag(tag, pageable);
        return ResponseEntity.ok().body(search);
    }
}
