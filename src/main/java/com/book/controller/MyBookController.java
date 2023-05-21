package com.book.controller;

import com.book.config.interceptor.Auth;
import com.book.utils.jwt.LoginUser;
import com.book.domain.MyBook.MyBook;
import com.book.service.mybook.dto.request.MyBookCreateDto;
import com.book.service.mybook.dto.request.MyBookUpdateDto;
import com.book.service.mybook.dto.response.MyBookResDto;
import com.book.service.mybook.MyBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mybook")
public class MyBookController {

    private final MyBookService myBookService;

    @Auth
    @PostMapping("/create")
    public ResponseEntity<Void> createMyBook(@LoginUser Long userId,
                                             @RequestBody MyBookCreateDto myBookDto) {
        myBookService.createMyBook(userId, myBookDto);
        return ResponseEntity.ok().build();
    }

    @Auth
    @GetMapping("/detail")
    public ResponseEntity<MyBookResDto> getMyBook(@LoginUser Long userId,
                                                  @RequestParam(name = "mybookid") Long id){
        myBookService.checkAccessPermission(id,userId);
        return ResponseEntity.ok(myBookService.getMyBook(id).toResDto());
    }

    @GetMapping("/shelf")
    public ResponseEntity<List<MyBookResDto>> myBookList(@RequestParam(name = "userid") Long userId){
        List<MyBookResDto> userBookList = myBookService.getMyBookList(userId).stream().map(MyBook::toResDto)
                .collect(Collectors.toList());
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
                                       @RequestParam Long id){

        myBookService.checkAccessPermission(id, userId);
        myBookService.deleteMyBooks(id);

        return ResponseEntity.ok().build();
    }

}
