package com.sparta.boardwithcomment.dto;

import com.sparta.boardwithcomment.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;
    private String password;

    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
