package com.book.domain.MyBook.dto.request;

import com.book.domain.MyBook.MyBook;
import com.book.domain.book.Book;
import com.book.domain.book.BookStatus;
import com.book.domain.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyBookCreateDto {

    private String isbn;

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

}
