package com.book.service;

import com.book.domain.recommend.Like;
import com.book.domain.recommend.Recommend;
import com.book.domain.user.User;
import com.book.repository.recommend.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    private void addLike(User user, Recommend recommend){
        Like like = Like.builder()
                .user(user)
                .recommend(recommend)
                .build();
        likeRepository.save(like);
    }
}
