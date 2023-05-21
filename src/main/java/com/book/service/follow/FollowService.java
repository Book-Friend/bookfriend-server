package com.book.service.follow;

import com.book.domain.follow.Follow;
import com.book.domain.user.User;
import com.book.repository.user.FollowRepository;
import com.book.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    public boolean isFollowed(User following, User follower){
        if(followRepository.existsByFollowingAndFollower(following, follower)){
            return true;
        }
        return false;
    }

    @Transactional
    public Follow follow(User following, User follower){
        if(isFollowed(following, follower)){
            throw new RuntimeException("이미 팔로우 중인 유저입니다.");
        }
        Follow newFollow = Follow.builder()
                .following(following)
                .follower(follower)
                .build();
        Follow follow = followRepository.save(newFollow);
        follower.addFollower(newFollow);
        following.addFollowing(newFollow);
        return follow;
    }

    @Transactional
    public void followCancel(User following, User follower){
        Follow follow = followRepository.findByFollowingAndFollower(following, follower).orElseThrow(
                () -> new RuntimeException("팔로우 중인 유저가 아닙니다."));

        following.deleteFollowing(follow);
        follower.deleteFollower(follow);
        System.out.println("following.getFollowing().size() = " + following.getFollowing().size());
        followRepository.deleteById(follow.getId());
    }
}
