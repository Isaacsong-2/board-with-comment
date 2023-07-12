package com.sparta.boardwithcomment.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.boardwithcomment.dto.LoginRequestDto;
import com.sparta.boardwithcomment.entity.RefreshToken;
import com.sparta.boardwithcomment.entity.UserRoleEnum;
import com.sparta.boardwithcomment.common.security.UserDetailsImpl;
import com.sparta.boardwithcomment.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthenticationSuccessHandler authenticationSuccessHandler, RefreshTokenService refreshTokenService) {
        this.jwtUtil = jwtUtil;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/auth/login");
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken();
        refreshTokenService.saveToken(refreshToken, ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId());

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
        log.info("로그인성공");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}