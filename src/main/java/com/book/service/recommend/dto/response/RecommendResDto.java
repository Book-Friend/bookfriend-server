package com.book.service.recommend.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RecommendResDto {

    private Long userId;
    private String nickname;
    private String userImage;
    private String title;
    private String bookImage;
    private String content;
    private int like;
    private List<String> tags;
}
