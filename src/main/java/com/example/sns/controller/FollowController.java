package com.example.sns.controller;

import com.example.sns.dto.FollowResponse;
import com.example.sns.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    // 닉네임으로 팔로우 — feed.html 검색창에서 사용
    @PostMapping("/users/follow-by-nickname")
    public ResponseEntity<Void> followByNickname(
            @RequestParam String nickname,
            HttpServletRequest httpRequest) {
        Long followerId = (Long) httpRequest.getAttribute("userId");
        followService.followByNickname(followerId, nickname);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // userId로 팔로우 (기존 유지)
    @PostMapping("/users/{userId}/follow")
    public ResponseEntity<Void> follow(
            @PathVariable Long userId,
            HttpServletRequest httpRequest) {
        Long followerId = (Long) httpRequest.getAttribute("userId");
        followService.follow(followerId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 언팔로우 (기존 유지)
    @DeleteMapping("/users/{userId}/follow")
    public ResponseEntity<Void> unfollow(
            @PathVariable Long userId,
            HttpServletRequest httpRequest) {
        Long followerId = (Long) httpRequest.getAttribute("userId");
        followService.unfollow(followerId, userId);
        return ResponseEntity.noContent().build();
    }

    // 팔로워 목록 (기존 유지)
    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<List<FollowResponse>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    // 팔로잉 목록 (기존 유지)
    @GetMapping("/users/{userId}/followings")
    public ResponseEntity<List<FollowResponse>> getFollowings(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowings(userId));
    }
}