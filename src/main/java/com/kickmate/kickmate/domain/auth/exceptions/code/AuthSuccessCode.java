package com.kickmate.kickmate.domain.auth.exceptions.code;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import com.kickmate.kickmate.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthSuccessCode implements BaseSuccessCode {

    AUTH_LOGIN_SUCCESS(HttpStatus.OK,
            "LOGIN_200",
            "로그인에 성공했습니다"),
    AUTH_LOGOUT_SUCCESS(HttpStatus.OK,
            "LOGOUT_200",
            "로그아웃에 성공했습니다"),
    AUTH_SIGNUP_SUCCESS(HttpStatus.OK,
            "SIGNUP_200",
            "회원가입에 성공했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
