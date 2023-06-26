package com.sparta.boardwithcomment.controller;

import com.sparta.boardwithcomment.dto.PostsRequestDto;
import com.sparta.boardwithcomment.dto.PostsResponseDto;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import com.sparta.boardwithcomment.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/post")
    public PostsResponseDto save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostsRequestDto requestDto){
        return postsService.save(userDetails, requestDto);
    }

    @GetMapping("/posts")
    public List<PostsResponseDto> findAll(){
        return postsService.findAll();
    }

    @GetMapping("/posts/{id}")
    public PostsResponseDto findOne(@PathVariable Long id){
        return postsService.findOne(id);
    }

    @PutMapping("/posts/{id}")
    public PostsResponseDto update(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostsRequestDto requestDto) {
        return postsService.update(userDetails, id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public Map<String, Boolean> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        boolean isDeleted = postsService.delete(userDetails, id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isDeleted);
        return response;
    }
}
