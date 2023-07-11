package com.sparta.boardwithcomment.controller;

import com.sparta.boardwithcomment.dto.LikePostsResponseDto;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import com.sparta.boardwithcomment.service.LikePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikePostsController {

    private final LikePostService likePostService;

    @PostMapping("/like/posts/{id}")
    public ResponseEntity<LikePostsResponseDto> like(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
        LikePostsResponseDto responseDto = likePostService.save(userDetails, id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/like/posts/{id}")
    public ResponseEntity<Long> dislike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
        return ResponseEntity.ok(likePostService.delete(userDetails, id));
    }
}
