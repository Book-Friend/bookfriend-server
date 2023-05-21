package com.book.service;

import com.book.domain.recommend.Recommend;
import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.service.recommend.dto.response.RecommendSearchDto;
import com.book.domain.tag.RecommendTag;
import com.book.domain.tag.Tag;
import com.book.service.user.dto.response.UserSearchDto;
import com.book.repository.recommend.RecommendRepository;
import com.book.repository.tag.TagRepository;
import com.book.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final RecommendRepository recommendRepository;

    public Page<UserSearchDto> searchUser(String email, Pageable pageable){
        return userRepository.findAllByEmail(email, pageable).map(UserSearchDto::from);
    }

    public List<RecommendResDto> searchRecommendByTag(String tagName, Pageable pageable) {
        Optional<Tag> tagOp = tagRepository.findByName(tagName);
        if(tagOp.isPresent()){
            Tag tag = tagOp.get();
            List<RecommendTag> recommendTags = tag.getRecommendTags();
            List<RecommendResDto> response = new ArrayList<>();
            for (RecommendTag recommendTag: recommendTags) {
                Recommend recommend = recommendTag.getRecommend();
                response.add(recommend.toResDto());
            }
            return response;
        }

        return null;
    }

    @Transactional
    public List<RecommendSearchDto> searchRecommend(String title, Pageable pageable) {
        List<Recommend> recommendList = recommendRepository.findRecommendByContentContaining(title);
        List<RecommendSearchDto> response = recommendList.stream().map(Recommend::toSearchDto).collect(Collectors.toList());
        return response;
    }

    @Transactional
    public Page<RecommendSearchDto> searchRecommendList(String keyword, Pageable pageable){
        return recommendRepository.searchRecommend(keyword, pageable);
    }
}
