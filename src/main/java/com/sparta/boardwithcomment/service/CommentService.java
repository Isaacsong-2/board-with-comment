package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.common.security.UserDetailsImpl;
import com.sparta.boardwithcomment.dto.CommentRequestDto;
import com.sparta.boardwithcomment.dto.CommentResponseDto;
import com.sparta.boardwithcomment.dto.CommentUpdateRequestDto;
import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.UserRoleEnum;
import com.sparta.boardwithcomment.repository.CommentRepository;
import com.sparta.boardwithcomment.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final PostsRepository postsRepository;
    private final CommentRepository commentRepository;
    private final MessageSource messageSource;

    public CommentResponseDto save(UserDetailsImpl userDetails, CommentRequestDto requestDto) {
        Posts posts = postsRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                "존재하지 않는 게시글입니다.",
                                Locale.getDefault()
                        )
                ));
        Comment comment;
        if (requestDto.getParentId() == 0) {
            comment = new Comment(requestDto.getContent(), posts, userDetails.getUser());
        } else {
            Comment parentComment = commentRepository.findById(requestDto.getParentId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
            );
            if (parentComment.getPosts().getId() != requestDto.getPostId())
                throw new IllegalArgumentException("같은 게시글의 댓글이 아닙니다.");
            comment = new Comment(requestDto.getContent(), posts, userDetails.getUser());

            comment.addParent(parentComment);
        }
        commentRepository.save(comment);
        return new CommentResponseDto(comment, userDetails.getUsername());
    }

    public CommentResponseDto update(Long id, UserDetailsImpl userDetails, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (userDetails.getUser().equals(comment.getUser()) || userDetails.getRole().equals(UserRoleEnum.ADMIN.toString())) {
            comment.update(requestDto);
        } else throw new IllegalArgumentException(
                messageSource.getMessage(
                        "not.authenticated",
                        null,
                        "수정/삭제 권한이 없습니다.",
                        Locale.getDefault()
                )
        );
        return new CommentResponseDto(comment, userDetails.getUsername());
    }

    public void delete(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if (userDetails.getUser().equals(comment.getUser()) || userDetails.getRole().equals(UserRoleEnum.ADMIN.toString())) {
            commentRepository.delete(comment);
        } else throw new IllegalArgumentException(
                messageSource.getMessage(
                        "not.authenticated",
                        null,
                        "수정/삭제 권한이 없습니다.",
                        Locale.getDefault()
                )
        );
    }
}
