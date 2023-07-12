package com.sparta.boardwithcomment.repository;

import com.sparta.boardwithcomment.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByOrderByCreatedAtDesc();

    Page<Posts> findAllByIdIn(List<Long> postIds, Pageable pageable);
}
