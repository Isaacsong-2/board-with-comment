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
//    @Column(nullable = false)
//    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "posts", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<LikePost> likePostList = new ArrayList<>();

    @Builder
    public Posts(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(PostsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
