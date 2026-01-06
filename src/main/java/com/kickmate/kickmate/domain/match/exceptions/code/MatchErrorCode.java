package com.kickmate.kickmate.domain.match.exceptions.code;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum MatchErrorCode implements BaseErrorCode {


    MATCH_INFO_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "MATCH_FILTER_400",
            "해당 경기의 match_info 를 찾을 수 없습니다."
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}


