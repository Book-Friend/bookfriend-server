package com.book.repository.recommend;

import com.book.domain.recommend.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Long>, RecommendRepositoryCustom {

    List<Recommend> findAllByUserId(Long userId);


    //@Query("select t from Recommend t join fetch t.book b where b.title = :title")
    //List<Recommend> findRecommendByContentContaining(@Param(value = "title") String title);

    List<Recommend> findRecommendByContentContaining(String keyword);
}
