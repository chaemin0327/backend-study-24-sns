package com.example.sns.service;

import com.example.sns.dto.FollowResponse;
import com.example.sns.entity.Follow;
import com.example.sns.entity.User;
import com.example.sns.exception.CustomException;
import com.example.sns.exception.ErrorCode;
import com.example.sns.repository.FollowRepository;
import com.example.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new CustomException(ErrorCode.CANNOT_FOLLOW_YOURSELF);
        }
        User follower = getUser(followerId);
        User following = getUser(followingId);
        validateNotAlreadyFollowing(follower, following);
        followRepository.save(new Follow(follower, following));
    }

    // 닉네임으로 팔로우 — feed.html 팔로우 검색창에서 사용
    @Transactional
    public void followByNickname(Long followerId, String nickname) {
        User following = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        follow(followerId, following.getId());
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        User follower = getUser(followerId);
        User following = getUser(followingId);
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new CustomException(ErrorCode.FOLLOW_NOT_FOUND));
        followRepository.delete(follow);
    }

    public List<FollowResponse> getFollowers(Long userId) {
        User user = getUser(userId);
        return followRepository.findFollowersByUser(user).stream()
                .map(follow -> FollowResponse.from(follow.getFollower()))
                .collect(Collectors.toList());
    }

    public List<FollowResponse> getFollowings(Long userId) {
        User user = getUser(userId);
        return followRepository.findFollowingsByUser(user).stream()
                .map(follow -> FollowResponse.from(follow.getFollowing()))
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private void validateNotAlreadyFollowing(User follower, User following) {
        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new CustomException(ErrorCode.ALREADY_FOLLOWING);
        }
    }
}