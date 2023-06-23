package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.dto.PostsRequestDto;
import com.sparta.boardwithcomment.dto.PostsResponseDto;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.repository.PostsRepository;
import com.sparta.boardwithcomment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {

    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    public PostsResponseDto save(PostsRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println(username);
        requestDto.setUsername(username);
        return new PostsResponseDto(postsRepository.save(requestDto.toEntity()));
    }

    public List<PostsResponseDto> findAll() {
        return postsRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(PostsResponseDto::new)
                .collect(Collectors.toList());
    }


    public PostsResponseDto findOne(Long id) {
        return new PostsResponseDto(postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")));
    }

    @Transactional
    public PostsResponseDto update(Long id, PostsRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
        if (posts.getUsername().equals(authentication.getName())) {
            posts.update(requestDto);
            return new PostsResponseDto(posts);
        }
        return null;
    }

    @Transactional
    public boolean delete(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
        if (posts.getUsername().equals(authentication.getName())) {

            postsRepository.delete(posts);
            return true;
        }
        return false;
    }
}
