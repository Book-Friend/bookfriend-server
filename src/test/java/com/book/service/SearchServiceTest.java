package com.book.service;

import com.book.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class SearchServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private BookSearchService bookSearchService;


//    @Test
//    @DisplayName("Add Search Detail | Success")
//    void addSearchDetailSuccess() {
//        User user = userRepository.findByEmail("test@test.com").orElse(null);
//        assertNotNull(user);
//        int bookCnt = user.getUserBooks().size();
//
//        SearchReqDto searchReqDto = getSearchReqDto("done", "2023-01-01", 2000, 1);
//        assertNotNull(searchService.addSearchDetail(user, searchReqDto));
//        assertEquals(bookCnt + 1, user.getBooks().size());
//    }
//
//    @Test
//    @DisplayName("Add Search Detail | Success : Null Page")
//    void addSearchDetailSuccessNullPage() {
//        User user = userRepository.findByEmail("test@test.com").orElse(null);
//        assertNotNull(user);
//        int bookCnt = user.getUserBooks().size();
//
//        SearchReqDto searchReqDto = getSearchReqDto("done", "2021-07-26", null, 1);
//        assertNull(searchService.addSearchDetail(user, searchReqDto));
//        assertEquals(bookCnt + 1, user.getUserBooks().size());
//    }
//
//    @Test
//    @DisplayName("Add Search Detail | Success : READING")
//    void addSearchDetailSuccessReading() {
//        User user = userRepository.findByEmail("test@test.com").orElse(null);
//        assertNotNull(user);
//        int bookCnt = user.getBooks().size();
//
//        SearchReqDto searchReqDto = getSearchReqDto("reading", "2021-07-26", 2000, 1);
//        assertNull(searchService.addSearchDetail(user, searchReqDto));
//        assertEquals(bookCnt + 1, user.getBooks().size());
//    }
//
//    @Test
//    @DisplayName("Add Search Detail | Fail : Invalid Date")
//    void addSearchDetailFailInvalidDate() throws Exception {
//        User user = userRepository.findByEmail("test@test.com").orElse(null);
//        assertNotNull(user);
//        SearchReqDto searchReqDto = getSearchReqDto("done", "2021-07-20", 2000, 1);
//
//        InvalidReqBodyException e = assertThrows(InvalidReqBodyException.class, () ->
//                searchService.addSearchDetail(user, searchReqDto));
//        assertEquals("date = 2021-07-26 < 2021-07-20", e.getMessage());
//    }
//
//    @Test
//    @DisplayName("Add Search Detail | Fail : Invalid Page")
//    void addSearchDetailFailInvalidPage() throws Exception {
//        User user = userRepository.findByEmail("test@test.com").orElse(null);
//        assertNotNull(user);
//        SearchReqDto searchReqDto = getSearchReqDto("done", "2021-07-26", 10, 1000);
//
//        InvalidReqBodyException e = assertThrows(InvalidReqBodyException.class, () ->
//                searchService.addSearchDetail(user, searchReqDto));
//        assertEquals("page = 1000 > 10", e.getMessage());
//    }
//
//    @Test
//    @DisplayName("Add Search Detail | Fail : Duplicate Book")
//    void addSearchDetailFailDuplicateBook() throws Exception {
//        User user = userRepository.findByEmail("test@test.com").orElse(null);
//        assertNotNull(user);
//        SearchReqDto searchReqDto1 = getSearchReqDto("done", "2021-07-26", 1000, 11);
//        SearchReqDto searchReqDto2 = getSearchReqDto("reading", "2021-07-26", 1001, 10);
//
//        DuplicateException e = assertThrows(DuplicateException.class, () -> {
//            searchService.addSearchDetail(user, searchReqDto1);
//            searchService.addSearchDetail(user, searchReqDto2);
//        });
//        assertEquals("isbn = 1234567890 1234567890123", e.getMessage());
//    }

//    private SearchReqDto getSearchReqDto(String bookStatus, String endDate, Integer totPage, int readPage) {
//        return SearchReqDto.builder()
//                .title("Good Omens")
//                .url("http://blahblah")
//                .isbn("1234567890 1234567890123")
//                .bookStatus(bookStatus)
//                .startDate(LocalDate.parse("2021-07-26"))
//                .endDate(LocalDate.parse(endDate))
//                .score(5)
//                .totPage(totPage)
//                .readPage(readPage)
//                .build();
//    }
}