package com.example.sns.controller;

import com.example.sns.dto.UserLoginRequest;
import com.example.sns.dto.UserLoginResponse;
import com.example.sns.dto.UserSignUpRequest;
import com.example.sns.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserSignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<UserLoginResponse> reissue(@RequestHeader("Authorization") String authHeader) {
        String refreshToken = authHeader.replace("Bearer ", "");
        return ResponseEntity.ok(userService.reissue(refreshToken));
    }

    // 회원가입 실시간 이메일 중복 체크 — signup.html에서 blur 이벤트로 호출
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    // 회원가입 실시간 닉네임 중복 체크 — signup.html에서 blur 이벤트로 호출
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.existsByNickname(nickname));
    }
}