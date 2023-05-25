package com.book.repository.mybook;

import com.book.service.mybook.dto.response.MyBookResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MyBookRepositoryCustom {

    Slice<MyBookResDto> findMyBookList(Long userId, Pageable pageable);
}
