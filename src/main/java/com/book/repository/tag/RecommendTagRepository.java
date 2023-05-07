package com.book.repository.tag;

import com.book.domain.tag.RecommendTag;
import com.book.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendTagRepository extends JpaRepository<RecommendTag, Long> {

    @Query("select t from RecommendTag t join fetch t.recommend m where t.tag = :tag")
    List<RecommendTag> findAllRecommends(@Param(value = "tag") Tag tag);
}
