package com.sparta.boardwithcomment.entity;

import com.sparta.boardwithcomment.dto.PostsRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Posts extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "posts")
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Posts(String title, String content, String username){
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public void update(PostsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
