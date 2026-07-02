package com.example.sns.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // USER 분야 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // POST 분야 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "해당 작업에 대한 권한이 없습니다."),

    // COMMENT 분야 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    // LIKE 분야 에러
    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다."),

    // FOLLOW 분야 에러
    ALREADY_FOLLOWING(HttpStatus.BAD_REQUEST, "이미 팔로우 중입니다."),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우 관계를 찾을 수 없습니다."),
    CANNOT_FOLLOW_YOURSELF(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우할 수 없습니다."),

    // AUTH (인증) 분야 에러
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다."),
    NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    }
