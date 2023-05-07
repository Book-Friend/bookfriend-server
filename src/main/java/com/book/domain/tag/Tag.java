package com.book.domain.tag;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag")
    private List<RecommendTag> recommendTags = new ArrayList<>();

    @Builder
    public Tag(String name){
        this.name = name;
    }

    //== 연관관계 메서드 ==//

    public void addTag(RecommendTag recommendTag){
        this.recommendTags.add(recommendTag);
    }

    public boolean deleteRecommendTag(RecommendTag recommendTag) {
        this.recommendTags.remove(recommendTag);
        if (this.recommendTags.isEmpty()) {
            return true;
        }
        return false;
    }
}
