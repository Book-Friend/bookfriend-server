package com.book.service.user.dto.response;

import com.book.domain.MyBook.MyBook;
import com.book.service.mybook.dto.response.MyBookResDto;
import com.book.service.recommend.dto.response.RecommendResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfileResDto {

    private String name;

    private String intro;

    private List<MyBookResDto> myBooks = new ArrayList<>();

    private List<RecommendResDto> recommends = new ArrayList<>();

    @Builder
    public ProfileResDto(String name, String intro){
        this.name = name;
        this.intro = intro;
    }

    public void setMyBooks(List<MyBook> books){
        for (MyBook book : books) {
            myBooks.add(book.toResDto());
        }
    }

    public void setRecommends(List<RecommendResDto> recommendList){
        recommends = recommendList;
    }
}
