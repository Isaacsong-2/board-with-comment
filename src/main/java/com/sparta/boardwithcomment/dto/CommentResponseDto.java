package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto{
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;

    public CommentResponseDto(Long id, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, String username) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.username = username;
    }

    public CommentResponseDto(Comment comment, String username) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = username;
    }
}
