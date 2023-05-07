package com.book.controller;

import com.book.domain.book.dto.response.BookCommentResDto;
import com.book.domain.book.dto.response.BookResDto;
import com.book.domain.book.dto.response.SearchDto;
import com.book.service.BookSearchService;
import com.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class BookSearchController {

    private final BookSearchService bookSearchService;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<SearchDto> search(
            @RequestParam String keyword,
            @RequestParam int start) {
        SearchDto search = bookSearchService.search(keyword, start);

        return ResponseEntity.ok().body(search);
    }

//    @GetMapping("/comment")
//    public ResponseEntity<Page<BookCommentResDto>> searchComment(@PageableDefault Pageable pageable,
//                                                                 @RequestParam String isbn){
//        Page<BookCommentResDto> comment = bookSearchService.searchComment(isbn, pageable);
//        return ResponseEntity.ok(comment);
//    }

}
