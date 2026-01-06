package com.kickmate.kickmate.domain.commentary.exception.code;

import com.kickmate.kickmate.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentarySuccessCode implements BaseSuccessCode {


    COMMENTARY_FIRST_SUCCESS(HttpStatus.OK,
            "COMMENTARY_200",
            "필러멘트가 생성되었습니다"),
    COMMENTARY_SUCCESS(HttpStatus.OK,
            "COMMENTARY_200",
            "대본 요청이 생성되었습니다")
    ;


    private final HttpStatus status;
    private final String code;
    private final String message;
}
