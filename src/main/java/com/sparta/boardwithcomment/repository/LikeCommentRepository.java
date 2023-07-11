package com.sparta.boardwithcomment.repository;

import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.LikeComment;
import com.sparta.boardwithcomment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByUserAndComment(User user, Comment comment);
}
