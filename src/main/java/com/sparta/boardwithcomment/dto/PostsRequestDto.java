package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
