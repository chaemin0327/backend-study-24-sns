package com.example.sns.controller;

import com.example.sns.dto.CommentRequest;
import com.example.sns.dto.CommentResponse;
import com.example.sns.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 특정 게시글의 댓글 목록을 조회한다.
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            HttpServletRequest httpRequest,
            @Valid @RequestBody CommentRequest request) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(postId, userId, request));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest,
            @Valid @RequestBody CommentRequest request) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        // postId도 같이 넘겨서 Service에서 소속 검증
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, userId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {

        Long userId = (Long) httpRequest.getAttribute("userId");
        // postId도 같이 넘겨서 Service에서 소속 검증
        commentService.deleteComment(postId, commentId, userId);
        return ResponseEntity.noContent().build();
    }
}