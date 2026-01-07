package com.kickmate.kickmate.domain.auth.controller;

import com.kickmate.kickmate.domain.auth.dto.*;
import com.kickmate.kickmate.domain.auth.exceptions.code.AuthSuccessCode;
import com.kickmate.kickmate.domain.auth.service.AuthService;
import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryReq;
import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryRes;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentarySuccessCode;
import com.kickmate.kickmate.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;



    @PostMapping("/signup")
    public ApiResponse<?> signUp(@RequestBody @Valid SignupReq req) {
        authService.signUp(req);

        return ApiResponse.onSuccess(AuthSuccessCode.AUTH_SIGNUP_SUCCESS,null);



    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody @Valid LoginReq req) {

        return ApiResponse.onSuccess(AuthSuccessCode.AUTH_LOGIN_SUCCESS,authService.login(req));

    }

    @PostMapping("/logout")
    public ApiResponse<?> logout() {


        //  프론트에서 토큰 삭제
        return ApiResponse.onSuccess(AuthSuccessCode.AUTH_LOGOUT_SUCCESS,null);



    }
}
