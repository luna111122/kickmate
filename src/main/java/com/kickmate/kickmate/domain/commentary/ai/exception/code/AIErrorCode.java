package com.kickmate.kickmate.domain.commentary.ai.exception.code;


import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AIErrorCode implements BaseErrorCode {

    ACTION_EVENT_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "COMMENTARY_400",
            "해당 경기의 raw_data 를 찾을 수 없습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
