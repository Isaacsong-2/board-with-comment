package com.sparta.boardwithcomment.repository;

import com.sparta.boardwithcomment.entity.Category;
import com.sparta.boardwithcomment.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    List<PostCategory> findAllByCategory(Category category);
}
