package com.book.domain.recommend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RecommendSearchDto {

    private Long id;
    private String nickname;
    private String title;
    private String bookImage;
    private String content;
}
