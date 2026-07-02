package com.example.sns.repository;

import com.example.sns.entity.Post;
import com.example.sns.entity.PostLike;
import com.example.sns.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // 특정 유저가 특정 게시글에 남긴 좋아요를 조회한다.
    Optional<PostLike> findByUserAndPost(User user, Post post);

    // 이미 좋아요를 눌렀는지 확인한다.
    boolean existsByUserAndPost(User user, Post post);

    // 게시글의 좋아요 개수를 센다.
    long countByPost(Post post);
}