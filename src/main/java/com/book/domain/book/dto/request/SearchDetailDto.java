package com.book.domain.book.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDetailDto {

    List<Items> item = new ArrayList<>();

    public static class Items{

        public String title;
        public String link;
        public String cover;
        public String author;
        public String publisher;
        public String isbn13;
        public String description;
        public subInfo subInfo;
    }

    public static class subInfo {
        public Integer itemPage;
    }

}
