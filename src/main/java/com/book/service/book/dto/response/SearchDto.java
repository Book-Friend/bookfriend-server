package com.book.service.book.dto.response;

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
public class SearchDto {

    List<Items> items = new ArrayList<>();

    public static class Items{
        public String title;
        public String author;
        public String isbn;
        public String description;
        public String image;
    }
}
