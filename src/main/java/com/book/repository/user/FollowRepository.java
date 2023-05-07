package com.book.repository.user;

import com.book.domain.follow.Follow;
import com.book.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowingAndFollower(User following, User follower);

    Optional<Follow> findByFollowingAndFollower(User following, User follower);
}
