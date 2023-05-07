package com.book.repository.recommend;


import com.book.domain.recommend.dto.response.RecommendSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecommendRepositoryCustom {

    public Page<RecommendSearchDto> searchRecommend(String keyword, Pageable pageable);
}
