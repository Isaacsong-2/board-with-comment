package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.dto.LikePostsResponseDto;
import com.sparta.boardwithcomment.entity.LikePost;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.User;
import com.sparta.boardwithcomment.repository.LikePostsRepository;
import com.sparta.boardwithcomment.repository.PostsRepository;
import com.sparta.boardwithcomment.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class LikePostService {

    private final LikePostsRepository likePostsRepository;
    private final PostsRepository postsRepository;
    private final MessageSource messageSource;
    public LikePostsResponseDto save(UserDetailsImpl userDetails, Long id) {
        User user = userDetails.getUser();
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                "존재하지 않는 게시글입니다.",
                                Locale.getDefault()
                        )
                ));
        if (likePostsRepository.findByUserAndPosts(user, posts).isPresent())
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "already.like.post",
                            null,
                            "이미 좋아요한 게시글입니다.",
                            Locale.getDefault()
                    )
            );
        return new LikePostsResponseDto(likePostsRepository.save(new LikePost(posts, userDetails.getUser())));
    }

    public Long delete(UserDetailsImpl userDetails, Long id) {
        User user = userDetails.getUser();
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                "존재하지 않는 게시글입니다.",
                                Locale.getDefault()
                        )
                ));
        LikePost likePost = likePostsRepository.findByUserAndPosts(user, posts)
                .orElseThrow(() -> new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.like.post",
                                null,
                                "좋아요를 누르지 않은 게시글입니다.",
                                Locale.getDefault()
                        )
                ));
        likePostsRepository.delete(likePost);
        return id;
    }
}
