package com.sparta.boardwithcomment.controller;

import com.sparta.boardwithcomment.dto.CommentRequestDto;
import com.sparta.boardwithcomment.dto.CommentUpdateRequestDto;
import com.sparta.boardwithcomment.security.UserDetailsImpl;
import com.sparta.boardwithcomment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> save(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto){
        checkToken(userDetails);
        return ResponseEntity.ok(commentService.save(userDetails, requestDto));

    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentUpdateRequestDto requestDto){
        checkToken(userDetails);
        return ResponseEntity.ok(commentService.update(id, userDetails, requestDto));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        checkToken(userDetails);
        commentService.delete(id, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공");
    }

    private void checkToken(UserDetailsImpl userDetails) {
        if (userDetails == null) throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
    }

}
