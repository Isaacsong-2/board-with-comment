package com.sparta.boardwithcomment.common.exception;

import lombok.Getter;

@Getter
public class ExceptionResponseDto {
    private int code;
    private String msg;

    public ExceptionResponseDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
