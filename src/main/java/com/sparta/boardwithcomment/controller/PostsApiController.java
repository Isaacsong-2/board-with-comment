package com.sparta.boardwithcomment.controller;

import com.sparta.boardwithcomment.dto.PostsRequestDto;
import com.sparta.boardwithcomment.dto.PostsResponseDto;
import com.sparta.boardwithcomment.service.PostsService;
import lombok.RequiredArgsConstructor;
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
    public PostsResponseDto save(@RequestBody PostsRequestDto requestDto){
        return postsService.save(requestDto);
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
    public PostsResponseDto update(@PathVariable Long id, @RequestBody PostsRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public Map<String, Boolean> delete(@PathVariable Long id) {
        boolean isDeleted = postsService.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isDeleted);
        return response;
    }
}
