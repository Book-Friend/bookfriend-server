package com.book.service.recommend.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecommendCreate {

    private Long bookId;
    private String content;
    private List<String> tags;
}
