package com.book.domain.recommend;

import com.book.domain.book.Book;
import com.book.service.recommend.dto.response.RecommendResDto;
import com.book.service.recommend.dto.response.RecommendSearchDto;
import com.book.domain.tag.RecommendTag;
import com.book.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recommend {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String content;

    @OneToMany(mappedBy = "recommend")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "recommend")
    private List<RecommendTag> tags = new ArrayList<>();

    private LocalDateTime createTime;

    @PrePersist
    public void createTime(){
        this.createTime = LocalDateTime.now();
    }

    public void addRec(){
        this.user.addRec(this);
        this.book.addRec(this);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }
    public void deleteLike(Like like) {
        this.likes.remove(like);
    }

    public int likesCount(){
        return likes.size();
    }

    public void addTag(RecommendTag tag){
        this.tags.add(tag);
    }

    public void deleteTags(){
        this.tags.clear();
    }

    @Builder
    public Recommend(Book book, User user, String content){
        this.book = book;
        this.user = user;
        this.content = content;
    }



    public RecommendResDto toResDto(){
        return RecommendResDto.builder()
                .userId(user.getId())
                .userImage(user.getImage())
                .nickname(user.getName())
                .title(book.getTitle())
                .bookImage(book.getImage())
                .content(content)
                .like(likesCount())
                .tags(tags.stream().map(RecommendTag::getTagName).collect(Collectors.toList()))
                .build();
    }

    public RecommendSearchDto toSearchDto(){
        return RecommendSearchDto.builder()
                .id(this.id)
                .nickname(user.getName())
                .title(book.getTitle())
                .bookImage(book.getImage())
                .content(content)
                //.tags(tags.stream().map(RecommendTag::getTagName).collect(Collectors.toList()))
                .build();
    }
}
