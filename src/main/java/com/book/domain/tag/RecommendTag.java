package com.book.domain.tag;

import com.book.domain.recommend.Recommend;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendTag {

    @Id
    @GeneratedValue
    @Column(name = "recommendTag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_id")
    private Recommend recommend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    private String tagName;

    public boolean deleteTag(){
        return this.tag.deleteRecommendTag(this);
    }
}
