package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.utils.jwt.LoginUser;
import com.book.domain.MyBook.MyBook;
import com.book.service.mybook.dto.request.MyBookCreateDto;
import com.book.service.mybook.dto.request.MyBookUpdateDto;
import com.book.service.mybook.dto.response.MyBookResDto;
import com.book.service.mybook.MyBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mybook")
public class MyBookController {

    private final MyBookService myBookService;

    @Auth
    @PostMapping("/create")
    public ResponseEntity<Void> createMyBook(@LoginUser Long userId,
                                             @RequestBody MyBookCreateDto myBookDto) {
        myBookService.createMyBook(userId, myBookDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<MyBookResDto> getMyBook(@RequestParam(name = "mybookid") Long id){
        //myBookService.checkAccessPermission(id,userId);
        return ResponseEntity.ok(myBookService.getMyBook(id).toResDto());
    }

    @GetMapping("/shelf/{userid}")
    public ResponseEntity<Slice<MyBookResDto>> myBookList(@PathVariable(name = "userid") Long userId,
                                                         @PageableDefault Pageable pageable){
        Slice<MyBookResDto> userBookList = myBookService.getMyBookList(userId, pageable);
        return ResponseEntity.ok(userBookList);
    }

    @Auth
    @PostMapping("/update/{id}")
    public ResponseEntity<Void> update(@LoginUser Long userId,
                                       @RequestBody MyBookUpdateDto myBookDto,
                                       @PathVariable("id") Long id){

        myBookService.checkAccessPermission(id, userId);
        myBookService.updateMyBook(id, myBookDto);

        return ResponseEntity.ok().build();
    }

    @Auth
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@LoginUser Long userId,
                                       @RequestParam("id") Long id){

        myBookService.checkAccessPermission(id, userId);
        myBookService.deleteMyBooks(id);

        return ResponseEntity.ok().build();
    }

}
