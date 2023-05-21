package com.book.service.tag;

import com.book.domain.recommend.Recommend;
import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.domain.tag.RecommendTag;
import com.book.domain.tag.Tag;
import com.book.repository.tag.RecommendTagRepository;
import com.book.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final RecommendTagRepository recommendTagRepository;

    @Transactional(readOnly = true)
    public List<RecommendResDto> searchTag(String name){
        Tag tag = tagRepository.findByName(name).get();

        List<RecommendTag> recommendTags = recommendTagRepository.findAllRecommends(tag);
        List<RecommendResDto> recommendList = new ArrayList<>();
        for (RecommendTag recommendTag : recommendTags) {
            Recommend recommend = recommendTag.getRecommend();
            recommendList.add(recommend.toResDto());
        }
        return recommendList;
    }

    @Transactional
    public void addTag(String name, Recommend recommend){
        Tag tag = getTag(name);
        RecommendTag recommendTag = RecommendTag.builder()
                .tag(tag)
                .recommend(recommend)
                .build();
        tag.addTag(recommendTag);
        recommend.addTag(recommendTag);
        recommendTagRepository.save(recommendTag);
    }

    public Tag getTag(String name){
        Optional<Tag> tag = tagRepository.findByName(name);
        if(tag.isEmpty()){
            return createTag(name);
        } else {
            return tag.get();
        }
    }

    public Tag createTag(String name){
        Tag tag = Tag.builder()
                .name(name)
                .build();
        tagRepository.save(tag);
        return tag;
    }

    @Transactional
    public void updateTag(Recommend recommend, List<String> tags){
        for (RecommendTag recommendTag : recommend.getTags()) {
            deleteTag(recommendTag);
        }
        recommend.deleteTags();
        for (String tagName: tags) {
            addTag(tagName, recommend);
        }
    }

    @Transactional
    public void deleteTag(RecommendTag recommendTag){
        Long tagId = recommendTag.getTag().getId();
        if(recommendTag.deleteTag()){
            tagRepository.deleteById(tagId);
        }
        recommendTagRepository.deleteById(recommendTag.getId());
    }

    @Transactional
    public void deleteTag(Recommend recommend){
        List<RecommendTag> tags = recommend.getTags();
        for (RecommendTag recommendTag: tags) {
            deleteTag(recommendTag);
        }
        recommend.deleteTags();
    }

}
