package com.sparta.boardwithcomment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private Long parentId;
    private String content;
}
