package com.sparta.boardwithcomment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardWithCommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardWithCommentApplication.class, args);
    }

}
