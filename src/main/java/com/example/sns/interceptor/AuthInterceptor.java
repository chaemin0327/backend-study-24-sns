package com.example.sns.interceptor;

import com.example.sns.exception.CustomException;
import com.example.sns.exception.ErrorCode;
import com.example.sns.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    // 컨트롤러 실행 전에 요청을 가로채어 토큰을 검증한다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 추가된 부분: 조회를 위한 GET 요청이나 CORS를 위한 OPTIONS 요청은 토큰이 없어도 통과시킨다.
        // 단, GET 요청이라도 토큰이 있으면 파싱해서 userId를 세팅해준다.
        // (예: 좋아요 여부 확인 GET /posts/{id}/likes 처럼 "로그인은 필요없지만 로그인했으면 내 정보가 필요한" API 대응)
        if (request.getMethod().equals("GET") || request.getMethod().equals("OPTIONS")) {
            String optionalHeader = request.getHeader("Authorization");
            if (optionalHeader != null && optionalHeader.startsWith("Bearer ")) {
                String optionalToken = optionalHeader.replace("Bearer ", "");
                if (jwtProvider.validateToken(optionalToken)) {
                    request.setAttribute("userId", jwtProvider.getUserIdFromToken(optionalToken));
                }
            }
            return true;
        }

        String header = request.getHeader("Authorization");

        // 헤더가 비어있거나 Bearer로 시작하지 않으면 예외를 발생시킨다.
        if (header == null || !header.startsWith("Bearer ")) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String token = header.replace("Bearer ", "");

        // 토큰 유효성을 검증하고 실패 시 예외를 발생시킨다.
        if (!jwtProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        // 검증 성공 시 요청 객체에 userId를 저장한다.
        Long userId = jwtProvider.getUserIdFromToken(token);
        request.setAttribute("userId", userId);

        return true;
    }
}