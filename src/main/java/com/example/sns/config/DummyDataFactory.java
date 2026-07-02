package com.example.sns.config;

import com.example.sns.entity.Post;
import com.example.sns.entity.User;
import com.example.sns.repository.PostRepository;
import com.example.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyDataFactory {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createDummyData() {
        // 해싱된 비밀번호를 역추적하기 쉽도록 SQL이 아닌 애플리케이션 레벨에서 생성한다.
        String encodedPassword = passwordEncoder.encode("Test1234");

        User testUser1 = new User("test1@gmail.com", "tester1", encodedPassword, "테스터1");
        User testUser2 = new User("test2@gmail.com", "tester2", encodedPassword, "테스터2");

        userRepository.save(testUser1);
        userRepository.save(testUser2);

        Post post1 = new Post("첫 번째 공지", "반갑습니다. 테스트 서버입니다.", testUser1);
        Post post2 = new Post("DDD 설계 테스트", "도메인 주도 설계 적용 완료!", testUser1);
        Post post3 = new Post("질문 있습니다", "게시글 수정은 어떻게 하나요?", testUser2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }
}