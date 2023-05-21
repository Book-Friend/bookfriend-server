package com.book.service.book.dto.response;

import com.book.domain.MyBook.MyBook;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookCommentResDto {

    private Long userId;
    private String Image;
    private String name;
    private String comment;
    private Integer star;
    private List<String> tags;

    public static BookCommentResDto from(MyBook myBook, List<String> tag){
        return BookCommentResDto.builder()
                .comment(myBook.getComment())
                .star(myBook.getStar())
                .Image(myBook.getUser().getImage())
                .name(myBook.getUser().getName())
                .userId(myBook.getUser().getId())
                .tags(tag)
                .build();
    }
}
