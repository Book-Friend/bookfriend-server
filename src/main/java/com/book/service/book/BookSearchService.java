package com.book.service.book;

import com.book.service.book.dto.response.BookResDto;
import com.book.service.book.dto.request.SearchDetailDto;
import com.book.service.book.dto.response.SearchDto;
import com.book.repository.book.BookRepository;
import com.book.repository.mybook.MyBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    @Value("${naver.id}")
    private String id;

    @Value("${naver.secret}")
    private String secret;

    @Value("${aladin.url}")
    private String DETAIL_URL;
    private final BookRepository bookRepository;
    private final MyBookRepository myBookRepository;

    private final String SEARCH_URL = "https://openapi.naver.com/v1/search/book.json";
    //private final String DETAIL_URL = "https://openapi.naver.com/v1/search/book_adv.xml";


    /*
    keyword : 검색어, start : 검색 시작 위치(기본 1, 10개씩 표시)
     */
    public SearchDto search(String keyword, int start){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = getHttpEntity();
        URI targetUrl = UriComponentsBuilder
                .fromUriString(SEARCH_URL)
                .queryParam("query", keyword)
                .queryParam("start", start)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        return restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, SearchDto.class).getBody();
    }

    public SearchDetailDto searchIsbn(String isbn) {
        RestTemplate restTemplate = new RestTemplate();

        URI targetUrl = UriComponentsBuilder
                .fromUriString(DETAIL_URL)
                .queryParam("ItemId", isbn)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        return restTemplate.exchange(targetUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), SearchDetailDto.class).getBody();
    }

    public boolean validIsbn(List<SearchDetailDto.Items> items, String isbn){
        if(!items.isEmpty() && (isbn.length() == 13 || isbn.length() == 10)){
            return true;
        }
        return false;
    }

    public BookResDto searchDetail(String isbn) {
        List<SearchDetailDto.Items> items = searchIsbn(isbn).getItem();
        if (!validIsbn(items, isbn))
            throw new InvalidParameterException("isbn = " + isbn);

        SearchDetailDto.Items item = items.get(0);
        BookResDto bookResDto = BookResDto.builder()
                .title(item.title)
                .link(item.link)
                .image(item.cover)
                .author(item.author)
                .publisher(item.publisher)
                .isbn(item.isbn13)
                .description(item.description)
                .page(item.subInfo.itemPage)
                .build();

        return bookResDto;
    }

//    public Page<BookCommentResDto> searchComment(String isbn, Pageable pageable){
//        Page<MyBook> myBooks = myBookRepository.findAllByIsbn(isbn);
//        Page<BookCommentResDto> comments =
//        return BmyBooks.map(BookCommentResDto::from);
//    }

    private HttpEntity<String> getHttpEntity() { //헤더에 인증 정보 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Naver-Client-Id", id);
        httpHeaders.set("X-Naver-Client-Secret", secret);
        return new HttpEntity<>(httpHeaders);
    }

}
