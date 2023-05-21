package com.book.service.recommend;

import com.book.domain.book.Book;
import com.book.domain.recommend.Recommend;
import com.book.service.recommend.dto.request.RecommendCreate;
import com.book.service.recommend.dto.request.RecommendUpdate;
import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.domain.tag.RecommendTag;
import com.book.domain.user.User;
import com.book.exception.book.BookNotFoundException;
import com.book.exception.book.UnAuthorizedAccess;
import com.book.repository.book.BookRepository;
import com.book.repository.recommend.RecommendRepository;
import com.book.service.tag.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;
    private final BookRepository bookRepository;
    private final TagService tagService;

    public void createRecommend(User user, RecommendCreate recommend){
        Book book = bookRepository.findById(recommend.getBookId()).orElseThrow(() -> new BookNotFoundException("존재하지 않는 책입니다."));
        Recommend rec = Recommend.builder()
                .book(book)
                .user(user)
                .content(recommend.getContent())
                .build();
        recommendRepository.save(rec);
        for (String tagName: recommend.getTags()) {
            tagService.addTag(tagName, rec);
        }
    }

    public void deleteRecommend(Long id) {
        Recommend recommend = getRecommend(id);
        tagService.deleteTag(recommend);
        User user = recommend.getUser();
        Book book = recommend.getBook();
        user.deleteRec(recommend);
        book.deleteRec(recommend);
        recommendRepository.deleteById(id);
    }

    public void updateRecommend(RecommendUpdate update, Recommend recommend) {
        recommend.updateContent(update.getContent());
        tagService.deleteTag(recommend);

        for (String tagName: update.getTags()) {
            tagService.addTag(tagName, recommend);
        }
    }

    public Recommend getRecommend(Long id) {
        return recommendRepository.findById(id).orElseThrow(() -> new BookNotFoundException("존재하지 않는 책입니다."));
    }

    public List<Recommend> getRecommendList(Long userId) {
        return recommendRepository.findAllByUserId(userId);
    }

    public RecommendResDto getRecommendRes(Recommend recommend){
        Book book = recommend.getBook();
        User user = recommend.getUser();
        List<String> tags = new ArrayList<>();
        List<RecommendTag> recommendTags = recommend.getTags();
        for (RecommendTag tag: recommendTags) {
            tags.add(tag.getTag().getName());
        }
        return RecommendResDto.builder()
                .title(book.getTitle())
                .bookImage(book.getImage())
                .userId(user.getId())
                .nickname(user.getName())
                        .userImage(user.getImage())
                .content(recommend.getContent())
                .tags(tags)
                .build();
    }

    public Recommend checkAccessPermission(Long id, Long userId){
        Recommend recommend = recommendRepository.findById(id).orElseThrow(() -> new BookNotFoundException("존재하지 않습니다."));
        if (!(recommend.getUser().getId()==userId)){
            throw new UnAuthorizedAccess("접근 권한이 없습니다.");
        }
        return recommend;
    }
}
