package com.sparta.boardwithcomment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Posts> postsList = new ArrayList<>();
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<LikePost> likePostList = new ArrayList<>();
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<LikeComment> likeCommentList = new ArrayList<>();
    @Builder
    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
