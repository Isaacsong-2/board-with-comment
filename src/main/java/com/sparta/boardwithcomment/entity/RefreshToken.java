package com.sparta.boardwithcomment.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String token;

    protected RefreshToken(){}

    public RefreshToken(Long userId, String token){
        this.userId = userId;
        this.token = token;
    }

    public void validateSameToken(String token) {
        System.out.println("----------------validate----------------");
        System.out.println(this);
        System.out.println(token);
        if (!this.token.equals(token)) {
            throw new IllegalArgumentException("토큰이 같지 않습니다.");
        }
    }
}
