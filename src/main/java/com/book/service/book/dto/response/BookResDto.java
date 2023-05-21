package com.book.service.book.dto.response;

import com.book.service.mybook.dto.response.MyBookResDto;
import com.book.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResDto{

    @NotNull
    private String title;
    @NotNull
    private String author;
    private String publisher;
    @NotNull
    @Pattern(regexp = "^.{10}\\s.{13}$")
    private String isbn;
    private String description;
    private String image;
    private String link;
    private Integer page;

    private List<MyBookResDto> bookList;

    public void addBook(MyBookResDto myBook) {
        bookList.add(myBook);
    }

    public Book toBook(){
        return Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .image(image)
                .description(description)
                .link(link)
                .page(page)
                .build();
    }
}
