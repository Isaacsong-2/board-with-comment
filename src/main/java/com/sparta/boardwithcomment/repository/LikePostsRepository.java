package com.sparta.boardwithcomment.repository;

import com.sparta.boardwithcomment.entity.LikePost;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostsRepository extends JpaRepository<LikePost, Long> {
    Optional<LikePost> findByUserAndPosts(User user, Posts posts);
}
