package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.common.security.UserDetailsImpl;
import com.sparta.boardwithcomment.dto.PostsRequestDto;
import com.sparta.boardwithcomment.dto.PostsResponseDto;
import com.sparta.boardwithcomment.entity.Category;
import com.sparta.boardwithcomment.entity.PostCategory;
import com.sparta.boardwithcomment.entity.Posts;
import com.sparta.boardwithcomment.entity.UserRoleEnum;
import com.sparta.boardwithcomment.repository.CategoryRepository;
import com.sparta.boardwithcomment.repository.PostCategoryRepository;
import com.sparta.boardwithcomment.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final MessageSource messageSource;
    public PostsResponseDto save(UserDetailsImpl userDetails, PostsRequestDto requestDto) {
        Posts posts = Posts.builder()
                            .title(requestDto.getTitle())
                            .content(requestDto.getContent())
                            .user(userDetails.getUser())
                            .build();
        List<String> categories = requestDto.getCategories();
        for (String categoryName : categories) {
            Category category = new Category(categoryName);
            Optional<Category> existCategory = categoryRepository.findByName(categoryName);
            if (existCategory.isEmpty()){
                postCategoryRepository.save(new PostCategory(posts, category));
            }
            else{
                postCategoryRepository.save(new PostCategory(posts, existCategory.get()));
            }
        }
        return new PostsResponseDto(posts);
    }

    public Page<PostsResponseDto> findAll(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return postsRepository.findAll(pageable).map(PostsResponseDto::new);
    }


    public PostsResponseDto findOne(Long id) {
        return new PostsResponseDto(postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                "존재하지 않는 게시글입니다.",
                                Locale.getDefault()
                        )
                )));
    }

    public Page<PostsResponseDto> findAllByCategory(int page, int size, String sortBy, boolean isAsc, String categoryName) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Category foundCategory = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        List<Long> postsIds = postCategoryRepository.findAllByCategory(foundCategory)
                .stream().map(category -> category.getPosts().getId()).toList();
        return postsRepository.findAllByIdIn(postsIds, pageable).map(PostsResponseDto::new);
    }

    @Transactional
    public PostsResponseDto update(UserDetailsImpl userDetails, Long id, PostsRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                "존재하지 않는 게시글입니다.",
                                Locale.getDefault()
                        )
                ));
        if (posts.getUser().getUsername().equals(userDetails.getUsername()) ||
                userDetails.getRole().equals(UserRoleEnum.ADMIN.toString())) {
            posts.update(requestDto);
            return new PostsResponseDto(posts);
        } else throw new IllegalArgumentException(
                messageSource.getMessage(
                        "not.authenticated",
                        null,
                        "수정/삭제 권한이 없습니다.",
                        Locale.getDefault()
                )
        );
    }

    @Transactional
    public void delete(UserDetailsImpl userDetails, Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.found.post",
                                null,
                                "존재하지 않는 게시글입니다.",
                                Locale.getDefault()
                        )
                ));
        if (posts.getUser().getUsername().equals(userDetails.getUsername()) ||
                userDetails.getRole().equals(UserRoleEnum.ADMIN.toString())) {
            postsRepository.delete(posts);
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
