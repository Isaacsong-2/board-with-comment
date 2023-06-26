package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.dto.CommentRequestDto;
import com.sparta.boardwithcomment.dto.CommentResponseDto;
import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.repository.CommentRepository;
import com.sparta.boardwithcomment.repository.PostsRepository;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto save(UserDetailsImpl userDetails, CommentRequestDto requestDto) {
        Posts posts = postsRepository.findById(requestDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Comment comment = new Comment(requestDto.getContent(), posts);
        if (posts.getUsername().equals(userDetails.getUsername())){
            commentRepository.save(comment);
        }
        return new CommentResponseDto(comment, posts.getUsername());
    }
}
