package com.book.domain.recommend.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class RecommendUpdate {

    private String content;

    private List<String> tags;

}
