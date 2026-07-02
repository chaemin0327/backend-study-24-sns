package com.example.sns.dto;

import com.example.sns.entity.Comment;

public record CommentResponse(
        Long id,
        String content,
        Long userId,
        String userName,
        Long postId
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getName(),
                comment.getPost().getId()
        );
    }
}