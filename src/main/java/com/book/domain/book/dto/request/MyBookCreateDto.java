package com.book.domain.book.dto.request;

import com.book.domain.MyBook.MyBook;
import com.book.domain.book.Book;
import com.book.domain.book.BookStatus;
import com.book.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyBookCreateDto {

    @NotNull
    private String title;

    private String author;

    private String publisher;

    @NotNull
    @Pattern(regexp = "^.{10}\\s.{13}$")
    private String isbn;

    private String description;

    private Integer page;

    @NotNull
    private String link;

    private String image;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer star;

    private String comment;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate endDate;

    @NotNull
    private BookStatus bookStatus;

    private List<String> tags;

    public void setBookStatus(String bookStatus) {
        switch (bookStatus) {
            case "DONE":
                this.bookStatus = BookStatus.DONE;
                break;
            case "READING":
                this.bookStatus = BookStatus.READING;
                break;
            case "WISH":
                this.bookStatus = BookStatus.WISH;
                break;
        }
    }

    public MyBook create(Book book, User user){
        return MyBook.builder()
                .book(book)
                .user(user)
                .isbn(isbn)
                .star(star)
                .comment(comment)
                .status(bookStatus)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public Book toBook(){
        return Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .description(description)
                .page(page)
                .link(link)
                .image(image)
                .build();
    }

}
