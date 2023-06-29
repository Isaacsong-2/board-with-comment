package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.dto.CommentRequestDto;
import com.sparta.boardwithcomment.dto.CommentResponseDto;
import com.sparta.boardwithcomment.dto.CommentUpdateRequestDto;
import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.UserRoleEnum;
import com.sparta.boardwithcomment.repository.CommentRepository;
import com.sparta.boardwithcomment.repository.PostsRepository;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto save(UserDetailsImpl userDetails, CommentRequestDto requestDto) {
        Posts posts = postsRepository.findById(requestDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Comment comment = new Comment(requestDto.getContent(), posts, userDetails.getUsername());
        commentRepository.save(comment);
        return new CommentResponseDto(comment, userDetails.getUsername());
    }

    public CommentResponseDto update(Long id, UserDetailsImpl userDetails, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (userDetails.getUsername().equals(comment.getUsername()) || userDetails.getRole().equals(UserRoleEnum.ADMIN.toString())) {
            comment.update(requestDto);
        } else throw new IllegalArgumentException("수정 권한이 없습니다.");
        return new CommentResponseDto(comment, userDetails.getUsername());
    }

    public void delete(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (userDetails.getUsername().equals(comment.getUsername()) || userDetails.getRole().equals(UserRoleEnum.ADMIN.toString())) {
            commentRepository.delete(comment);
        } else throw new IllegalArgumentException("삭제 권한이 없습니다.");
    }
}
