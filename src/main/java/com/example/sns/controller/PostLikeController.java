package com.example.sns.controller;

import com.example.sns.service.PostLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<Void> addLike(
            @PathVariable Long postId,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        postLikeService.addLike(postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLike(
            @PathVariable Long postId,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        postLikeService.removeLike(postId, userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<Boolean> checkLike(
            @PathVariable Long postId,
            HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        boolean liked = postLikeService.isLiked(postId, userId);
        return ResponseEntity.ok(liked);
    }
}