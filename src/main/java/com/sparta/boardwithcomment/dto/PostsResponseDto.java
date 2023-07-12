package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.PostCategory;
import com.sparta.boardwithcomment.entity.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostsResponseDto {
    private String title;
    private String content;
    private String username;
    private int likeNum;
    private List<String> categories;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentList;
    public PostsResponseDto(Posts posts) {
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.username = posts.getUser().getUsername();
        this.likeNum = posts.getLikePostList().size();
        this.createdAt = posts.getCreatedAt();
        this.modifiedAt = posts.getModifiedAt();
        this.commentList = posts.getCommentList().stream()
                .filter(childComment -> childComment.getParent() == null) // 필터링: 상위 레벨 댓글만 포함
                .map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed())
                .collect(Collectors.toList());
        this.categories = posts.getPostCategoryList().stream()
                .map((postCategory -> postCategory.getCategory().getName())).toList();
    }
}
