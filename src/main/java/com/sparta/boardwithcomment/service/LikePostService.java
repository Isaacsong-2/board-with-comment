package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.dto.LikePostsResponseDto;
import com.sparta.boardwithcomment.entity.LikePost;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.User;
import com.sparta.boardwithcomment.repository.LikePostsRepository;
import com.sparta.boardwithcomment.repository.PostsRepository;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikePostService {

    private final LikePostsRepository likePostsRepository;
    private final PostsRepository postsRepository;
    public LikePostsResponseDto save(UserDetailsImpl userDetails, Long id) {
        if (userDetails == null) throw new IllegalArgumentException("회원 정보가 없습니다.");
        User user = userDetails.getUser();
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        if (likePostsRepository.findByUserAndPosts(user, posts).isPresent())
            throw new IllegalArgumentException("이미 좋아요한 게시글입니다.");
        return new LikePostsResponseDto(likePostsRepository.save(new LikePost(posts, userDetails.getUser())));
    }

    public Long delete(UserDetailsImpl userDetails, Long id) {
        if (userDetails == null) throw new IllegalArgumentException("회원 정보가 없습니다.");
        User user = userDetails.getUser();
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));
        LikePost likePost = likePostsRepository.findByUserAndPosts(user, posts)
                .orElseThrow(() -> new IllegalArgumentException("좋아요를 누르지 않은 게시글입니다."));
        likePostsRepository.delete(likePost);
        return id;
    }
}
