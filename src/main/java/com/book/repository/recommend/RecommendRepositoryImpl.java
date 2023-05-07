package com.book.repository.recommend;

import com.book.domain.recommend.QRecommend;
import com.book.domain.recommend.Recommend;
import com.book.domain.recommend.dto.response.RecommendResDto;
import com.book.domain.recommend.dto.response.RecommendSearchDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.book.domain.book.QBook.book;
import static com.book.domain.recommend.QRecommend.recommend;
import static com.book.domain.tag.QTag.tag;
import static com.book.domain.user.QUser.user;

@RequiredArgsConstructor
public class RecommendRepositoryImpl implements RecommendRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RecommendSearchDto> searchRecommend(String keyword, Pageable pageable){
        List<RecommendSearchDto> content = queryFactory
                .select(Projections.fields(RecommendSearchDto.class,
                        recommend.id,
                        recommend.content,
                        user.name,
                        book.title,
                        book.image
                ))
                .from(recommend)
                .join(recommend.book, book)
                .join(recommend.user, user)
                .where(recommend.content.contains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory
                .selectFrom(recommend)
                .where(recommend.content.contains(keyword))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }


}
