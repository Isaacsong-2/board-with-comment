package com.sparta.boardwithcomment.controller;

import com.sparta.boardwithcomment.dto.PostsRequestDto;
import com.sparta.boardwithcomment.dto.PostsResponseDto;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import com.sparta.boardwithcomment.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            postsService.delete(userDetails, id);
            return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
