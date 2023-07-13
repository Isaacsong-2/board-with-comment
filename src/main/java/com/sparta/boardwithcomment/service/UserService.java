package com.sparta.boardwithcomment.service;

import com.sparta.boardwithcomment.dto.UserRequestDto;
import com.sparta.boardwithcomment.entity.User;
import com.sparta.boardwithcomment.entity.UserRoleEnum;
import com.sparta.boardwithcomment.common.jwt.JwtUtil;
import com.sparta.boardwithcomment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()){
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "duplicated.user",
                            null,
                            "중복된 사용자입니다.",
                            Locale.getDefault()
                    )
            );
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException(
                        messageSource.getMessage(
                                "incorrect.adminPassword",
                                null,
                                "관리자 암호가 틀려 등록이 불가합니다.",
                                Locale.getDefault()
                        )
                );
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    public void withdraw(User user) {
        userRepository.delete(user);
    }
}
