package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.common.jwt.JwtUtil;
import com.sparta.boardwithcomment.entity.RefreshToken;
import com.sparta.boardwithcomment.entity.User;
import com.sparta.boardwithcomment.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    public void saveToken(String refreshToken, Long userId) {
        refreshTokenRepository.save(new RefreshToken(userId, refreshToken));
    }

    @Transactional
    public void matches(String refreshToken, Long userId) {
        System.out.println(userId);
        RefreshToken savedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("토큰정보가 없습니다."));

        if (!jwtUtil.validateToken(savedToken.getToken())) {
            refreshTokenRepository.delete(savedToken);
            throw new IllegalArgumentException("올바르지 않은 토큰 정보입니다.");
        }
        savedToken.validateSameToken(refreshToken);
    }
}
