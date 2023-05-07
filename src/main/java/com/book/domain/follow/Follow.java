package com.book.domain.follow;

import com.book.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"follower", "following"}
                )
        }
)
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following")
    private User following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower")
    private User follower;

}
