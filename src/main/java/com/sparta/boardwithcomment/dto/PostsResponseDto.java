package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostsResponseDto(Posts posts) {
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.username = posts.getUsername();
        this.createdAt = posts.getCreatedAt();
        this.modifiedAt = posts.getModifiedAt();
    }
}
