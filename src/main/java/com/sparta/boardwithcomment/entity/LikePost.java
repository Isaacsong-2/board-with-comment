package com.sparta.boardwithcomment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LikePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public LikePost(Posts posts, User user) {
        this.posts = posts;
        this.user = user;
    }
}
