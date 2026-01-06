package com.kickmate.kickmate.domain.match.exceptions.code;

import com.kickmate.kickmate.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum MatchSuccessCode implements BaseSuccessCode {

    MATCH_FILTER_SUCCESS(HttpStatus.OK,
            "MATCH_FILTER_200",
            "필터링이 성공했습니다"),
    MATCH_INFO_SUCCESS(HttpStatus.OK,
            "MATCH_INFO_200",
            "경기 정보를 찾는데 성공했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
