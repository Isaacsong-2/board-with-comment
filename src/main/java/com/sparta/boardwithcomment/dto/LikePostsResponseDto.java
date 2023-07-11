package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.LikePost;
import lombok.Getter;

@Getter
public class LikePostsResponseDto {

    private String username;
    private Long postId;
    public LikePostsResponseDto(LikePost likePost) {
        this.username = likePost.getUser().getUsername();
        this.postId = likePost.getPosts().getId();
    }
}
