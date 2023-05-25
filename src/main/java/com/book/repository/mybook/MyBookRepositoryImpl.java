package com.book.repository.mybook;

import com.book.service.mybook.dto.response.MyBookResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;

import java.util.List;

import static com.book.domain.MyBook.QMyBook.myBook;
import static com.book.domain.book.QBook.book;
import static com.book.domain.user.QUser.user;

@RequiredArgsConstructor
public class MyBookRepositoryImpl implements MyBookRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<MyBookResDto> findMyBookList(Long userId, Pageable pageable) {
        List<MyBookResDto> content = queryFactory
                .select(Projections.fields(MyBookResDto.class,
                        book.title,
                        book.author,
                        book.image,
                        myBook.startDate,
                        myBook.endDate,
                        myBook.star,
                        myBook.comment,
                        myBook.id
                ))
                .from(myBook)
                .join(myBook.user, user).on(myBook.user.id.eq(userId))
                .join(myBook.book, book)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if(content.size() > pageable.getPageSize()){
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
