package com.kickmate.kickmate.domain.auth.exceptions.code;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    AUTH_SIGNUP_DUPLICATED(HttpStatus.BAD_REQUEST,
            "SIGNUP_400",
            "이미 존재하는 회원입니다"),
    AUTH_LOGIN_ERROR(HttpStatus.BAD_REQUEST,
            "LOGIN_400",
            "회원을 찾을 수 없습니당"),
    AUTH_LOGIN_FAIL(HttpStatus.BAD_REQUEST,
            "LOGIN_400",
            "비밀번호가 일치하지 않습니다");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
