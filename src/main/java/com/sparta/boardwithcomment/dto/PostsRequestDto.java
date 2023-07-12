package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostsRequestDto {
    private String title;
    private String content;
    private List<String> categories;
}
