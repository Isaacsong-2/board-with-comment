package com.sparta.boardwithcomment.controller;

import com.sparta.boardwithcomment.common.jwt.JwtUtil;
import com.sparta.boardwithcomment.common.security.UserDetailsImpl;
import com.sparta.boardwithcomment.dto.UserRequestDto;
import com.sparta.boardwithcomment.entity.User;
import com.sparta.boardwithcomment.service.RefreshTokenService;
import com.sparta.boardwithcomment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid UserRequestDto requestDto, BindingResult bindingResult){
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size()> 0 ){
            for (FieldError fieldError : fieldErrors){
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 저장 실패");
        }
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공");
    }

    @DeleteMapping("/auth/withdraw")
    public ResponseEntity<String> withdraw(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.withdraw(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴 성공");
    }

    @GetMapping("/auth/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal UserDetailsImpl userDetails){
        validateHeader(request);
        User user = userDetails.getUser();
        Long memberId = user.getId();
        String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);

        refreshTokenService.matches(refreshToken, memberId);

        String accessToken = jwtUtil.createAccessToken(user.getUsername(), user.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);
    }

    private void validateHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshTokenHeader = request.getHeader("RefreshToken");
        System.out.println(authorizationHeader);
        System.out.println(refreshTokenHeader);
        if (Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
            throw new IllegalArgumentException("토큰정보가 없습니다.");
        }
    }
}
