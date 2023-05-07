package com.book.domain.book;

import com.book.domain.MyBook.MyBook;
import com.book.domain.book.dto.response.BookResDto;
import com.book.domain.recommend.Recommend;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private String publisher;

    private String isbn;

    private Integer page;

    private String description;

    private String link;

    private String image;

    @OneToMany(mappedBy = "book")
    private List<MyBook> myBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Recommend> recommends = new ArrayList<>();

    @Builder
    public Book(String title, String author, String publisher, String isbn, Integer page, String description, String link, String image){
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.page = page;
        this.description = description;
        this.link = link;
        this.image = image;
    }

    public void addMyBook(MyBook myBook){
        this.myBooks.add(myBook);
    }

    public void deleteMyBook(MyBook myBook){
        this.myBooks.remove(myBook);
    }
    public void addRec(Recommend rec) {
        this.recommends.add(rec);
    }

    public int numberOfUser(){
        return myBooks.size();
    }

    public BookResDto toResDto(){
        return BookResDto.builder()
                .image(image)
                .description(description)
                .link(link)
                .publisher(publisher)
                .author(author)
                .page(page)
                .title(title)
                .isbn(isbn)
                .build();
    }

    public void deleteRec(Recommend recommend) {
        recommends.remove(recommend);
    }
}
