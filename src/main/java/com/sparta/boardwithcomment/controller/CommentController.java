package com.sparta.boardwithcomment.controller;

import com.sparta.boardwithcomment.dto.CommentRequestDto;
import com.sparta.boardwithcomment.dto.CommentResponseDto;
import com.sparta.boardwithcomment.dto.CommentUpdateRequestDto;
import com.sparta.boardwithcomment.dto.PostsResponseDto;
import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.repository.CommentRepository;
import com.sparta.boardwithcomment.repository.PostsRepository;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import com.sparta.boardwithcomment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentResponseDto save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto){
        return commentService.save(userDetails, requestDto);
    }

    @PutMapping("/comment/{id}")
    public CommentResponseDto update(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentUpdateRequestDto requestDto){
        return commentService.update(id, userDetails, requestDto);
    }
}
