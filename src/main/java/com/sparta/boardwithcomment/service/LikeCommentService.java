package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.dto.LikeCommentResponseDto;
import com.sparta.boardwithcomment.entity.Comment;
import com.sparta.boardwithcomment.entity.LikeComment;
import com.sparta.boardwithcomment.entity.User;
import com.sparta.boardwithcomment.repository.CommentRepository;
import com.sparta.boardwithcomment.repository.LikeCommentRepository;
import com.sparta.boardwithcomment.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    public LikeCommentResponseDto save(UserDetailsImpl userDetails, Long id) {
        if (userDetails == null) throw new IllegalArgumentException("회원 정보가 없습니다.");
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        if (likeCommentRepository.findByUserAndComment(user, comment).isPresent())
            throw new IllegalArgumentException("이미 좋아요한 댓글입니다.");
        return new LikeCommentResponseDto(likeCommentRepository.save(new LikeComment(comment, userDetails.getUser())));
    }


    public Long delete(UserDetailsImpl userDetails, Long id) {
        if (userDetails == null) throw new IllegalArgumentException("회원 정보가 없습니다.");
        User user = userDetails.getUser();
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        LikeComment likeComment = likeCommentRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않은 댓글입니다."));
        likeCommentRepository.delete(likeComment);
        return id;
    }
}
