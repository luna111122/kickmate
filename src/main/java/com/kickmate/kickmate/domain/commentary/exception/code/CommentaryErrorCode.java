package com.kickmate.kickmate.domain.commentary.exception.code;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentaryErrorCode implements BaseErrorCode {

    INVALID(HttpStatus.BAD_REQUEST,
            "COMMON400",
            "요청이 적절하지 않습니다."),

    FILLER_SCRIPT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "COMMENTARY_001",
            "해당 경기의 필러 스크립트를 찾을 수 없습니다."

    ), AI_REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,
            "COMMENTARY_002",
            "ai tts를 생성하지 못했습니다");



    private final HttpStatus status;
    private final String code;
    private final String message;
}
