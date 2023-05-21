package com.book.service;

import com.book.domain.MyBook.MyBook;
import com.book.service.mybook.dto.request.MyBookCreateDto;
import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.domain.user.User;
import com.book.service.user.dto.request.UserCreateDto;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TagServiceTest {

    @Autowired  private UserService userService;
    @Autowired private TagService tagService;
    @Autowired private MyBookService myBookService;
    private User user;
    private MyBook myBook;

    @BeforeEach
    public void init() {
        UserCreateDto createDto1 = new UserCreateDto("test", "test@kim.com", "1234");
        user = userService.signUp(createDto1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        MyBookCreateDto createDto = MyBookCreateDto.builder()
                .author("testAuthor")
                .description("testDescription")
                .title("testTitle")
                .page(1)
                .star(1)
                .isbn("1234512345123")
                .url("testUrl")
                .tags(tags)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        myBook = myBookService.createMyBook(user.getId(), createDto);
        tags.add("tag3");
        MyBookCreateDto createDto2 = MyBookCreateDto.builder()
                .author("testAuthor1")
                .description("testDescription1")
                .title("testTitle1")
                .page(1)
                .star(1)
                .isbn("1234512345122")
                .url("testUrl")
                .tags(tags)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        myBookService.createMyBook(user.getId(), createDto2);
    }


    @Test
    @DisplayName("태그 검색")
    void tagSearch(){
        List<RecommendResDto> myBooks = tagService.searchTag("tag1");
        assertEquals(myBooks.size(), 2);
        assertEquals(myBooks.get(0).getTitle(), "testTitle");
    }

}