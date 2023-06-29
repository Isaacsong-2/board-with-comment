package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
public class PostsResponseDto {
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<String> commentList;
    public PostsResponseDto(Posts posts) {
        List<String> commentList = new ArrayList<>();
        List<Comment> comments = posts.getCommentList();
        Collections.sort(comments, Comparator.comparing(Comment::getCreatedAt).reversed());
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.username = posts.getUser().getUsername();
        this.createdAt = posts.getCreatedAt();
        this.modifiedAt = posts.getModifiedAt();
        for (Comment comment : posts.getCommentList()) {
            commentList.add(comment.getContent());
        }
        this.commentList = commentList;
    }
}
