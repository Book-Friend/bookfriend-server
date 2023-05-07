package com.book.domain.alarm;

import com.book.domain.alarm.dto.response.NoticeResDto;
import com.book.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notice {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private LocalDateTime createdAt;

    private boolean checked;

    @PrePersist
    public void createTime(){
        this.createdAt = LocalDateTime.now();
    }

    public void read(){
        this.checked = true;
    }

    @Builder
    public Notice(String content, User user){
        this.content = content;
        this.user = user;
        this.checked = false;
    }

    public NoticeResDto toResDto(){
        return NoticeResDto.builder()
                .id(this.id)
                .content(this.content)
                .createdAt(this.createdAt)
                .checked(this.checked)
                .build();
    }

}
