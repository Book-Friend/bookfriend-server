package com.book.controller;

import com.book.config.auth.PrincipalDetails;
import com.book.domain.MyBook.MyBook;
import com.book.domain.MyBook.dto.request.MyBookCreateDto;
import com.book.domain.MyBook.dto.request.MyBookUpdateDto;
import com.book.domain.MyBook.dto.response.MyBookResDto;
import com.book.service.MyBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mybook")
public class MyBookController {

    private final MyBookService myBookService;

    @PostMapping("/create")
    public ResponseEntity<Void> createMyBook(@AuthenticationPrincipal PrincipalDetails userDetails,
                                              @RequestBody MyBookCreateDto myBookDto) {
        myBookService.createMyBook(userDetails.getUser().getId(), myBookDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<MyBookResDto> getMyBook(@AuthenticationPrincipal PrincipalDetails userDetails,
                                              @RequestParam(name = "mybookid") Long id){
        myBookService.checkAccessPermission(id, userDetails.getUsername());
        return ResponseEntity.ok(myBookService.getMyBook(id).toResDto());
    }

    @GetMapping("/shelf")
    public ResponseEntity<List<MyBookResDto>> myBookList(@RequestParam(name = "userid") Long userId){
        List<MyBookResDto> userBookList = myBookService.getMyBookList(userId).stream().map(MyBook::toResDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userBookList);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> update(@AuthenticationPrincipal PrincipalDetails userDetails,
                                       @RequestBody MyBookUpdateDto myBookDto,
                                       @PathVariable("id") Long id){

        myBookService.checkAccessPermission(id, userDetails.getUsername());
        myBookService.updateMyBook(id, myBookDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal PrincipalDetails userDetails,
                       @RequestParam Long id){

        myBookService.checkAccessPermission(id, userDetails.getUsername());
        myBookService.deleteMyBooks(id);

        return ResponseEntity.ok().build();
    }

}
