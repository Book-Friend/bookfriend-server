package com.book.service.book;

import com.book.domain.book.Book;
import com.book.service.book.dto.response.BookResDto;
import com.book.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookSearchService bookSearchService;

    public Book createBook(BookResDto bookDto){
        return bookRepository.save(bookDto.toBook());
    }

    public Book getBook(String isbn){
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if(book.isEmpty()){
            BookResDto bookResDto = bookSearchService.searchDetail(isbn);
            return createBook(bookResDto);
        } else {
            return book.get();
        }
    }
}
