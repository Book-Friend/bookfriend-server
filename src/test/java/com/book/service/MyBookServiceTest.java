package com.book.service;

import com.book.domain.MyBook.MyBook;
import com.book.service.mybook.dto.request.MyBookCreateDto;
import com.book.domain.tag.Tag;
import com.book.domain.user.User;
import com.book.service.user.dto.request.UserCreateDto;
import com.book.exception.book.BookNotFoundException;
import com.book.exception.book.DuplicateBookException;
import com.book.service.mybook.MyBookService;
import com.book.service.tag.TagService;
import com.book.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class MyBookServiceTest {

    @Autowired private UserService userService;
    @Autowired private MyBookService myBookService;
    @Autowired private TagService tagService;

    private User user;
    private MyBookCreateDto createDto;
    @BeforeEach
    public void init() {
        UserCreateDto createDto1 = new UserCreateDto("test", "test@kim.com", "1234");
        user = userService.signUp(createDto1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        createDto = MyBookCreateDto.builder()
                .star(1)
                .isbn("1234512345123")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("책 생성")
    void create() {
        MyBook myBook = myBookService.createMyBook(user.getId(), createDto);
        Tag tag1 = tagService.getTag("tag1");

        assertEquals(user.getMyBooks().isEmpty(), false);
        assertEquals(myBook.getIsbn(), "1234512345123");
        assertEquals(tag1.getMyBookTags().size(), 1);
    }

    @Test
    @DisplayName("책 중복 생성")
    void duplicateCreate(){
        MyBook myBook = myBookService.createMyBook(user.getId(), createDto);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        MyBookCreateDto createDto1 = MyBookCreateDto.builder()
                .author("test")
                .description("test")
                .page(1)
                .star(1)
                .isbn("1234512345123")
                .url("123")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .tags(tags)
                .build();
        assertThrows(DuplicateBookException.class, () -> myBookService.createMyBook(user.getId(), createDto1));
    }

    @Test
    @DisplayName("책 삭제 | 태그 삭제")
    void delete() {
        MyBook myBook = myBookService.createMyBook(user.getId(), createDto);
        Tag tag1 = tagService.getTag("tag1");
        myBookService.deleteMyBooks(myBook.getId());
        assertThrows(BookNotFoundException.class, () -> myBookService.getMyBook(myBook.getId()));
        assertEquals(tag1.getMyBookTags().size(), 0);
    }

    @Test
    @DisplayName("책 찾기")
    void findBook(){
        MyBook myBook = myBookService.createMyBook(user.getId(), createDto);
        MyBook myBook1 = myBookService.getMyBook(myBook.getId());
        assertEquals(myBook1, myBook);
    }


}