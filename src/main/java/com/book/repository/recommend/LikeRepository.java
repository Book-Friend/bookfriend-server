package com.book.repository.recommend;

import com.book.domain.recommend.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
