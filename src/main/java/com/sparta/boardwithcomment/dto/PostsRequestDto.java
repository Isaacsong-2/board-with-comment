package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsRequestDto {
    private String title;
    private String username;
    private String content;

    @Builder
    public PostsRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
    }
}
