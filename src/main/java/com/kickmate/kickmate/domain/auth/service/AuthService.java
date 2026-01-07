package com.kickmate.kickmate.domain.auth.service;

import com.kickmate.kickmate.domain.auth.dto.*;

import com.kickmate.kickmate.domain.auth.entity.Member;
import com.kickmate.kickmate.domain.auth.exceptions.AuthException;
import com.kickmate.kickmate.domain.auth.exceptions.code.AuthErrorCode;
import com.kickmate.kickmate.domain.auth.repository.MemberRepository;
import com.kickmate.kickmate.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(SignupReq req) {
        if (memberRepository.existsByUserId(req.getUserId())) {
            throw new AuthException(AuthErrorCode.AUTH_SIGNUP_DUPLICATED);
        }

        String hash = passwordEncoder.encode(req.getPassword());
        memberRepository.save(Member.create(req.getUserId(), hash));
    }

    @Transactional(readOnly = true)
    public LoginRes login(LoginReq req) {
        Member member = memberRepository.findByUserId(req.getUserId())
                .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_LOGIN_ERROR));

        if (!passwordEncoder.matches(req.getPassword(), member.getPasswordHash())) {
            throw new AuthException(AuthErrorCode.AUTH_LOGIN_FAIL);
        }

        String token = jwtProvider.createAccessToken(member.getId());
        return new LoginRes(token);
    }
}
