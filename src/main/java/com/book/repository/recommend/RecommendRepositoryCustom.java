package com.book.repository.recommend;


import com.book.service.recommend.dto.response.RecommendSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecommendRepositoryCustom {

    public Page<RecommendSearchDto> searchRecommend(String keyword, Pageable pageable);
}
