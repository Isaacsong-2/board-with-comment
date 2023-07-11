package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto{
    private Long id;
    private String content;
    private int likeNum;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String username;


    public CommentResponseDto(Comment comment, String username) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = username;
        this.likeNum = comment.getLikeCommentList().size();
    }

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.username = comment.getUser().getUsername();
        this.likeNum = comment.getLikeCommentList().size();
    }
}
