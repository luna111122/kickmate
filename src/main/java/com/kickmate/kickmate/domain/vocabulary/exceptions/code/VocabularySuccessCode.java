package com.kickmate.kickmate.domain.vocabulary.exceptions.code;

import com.kickmate.kickmate.global.apiPayload.code.BaseSuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VocabularySuccessCode implements BaseSuccessCode {


    VOCABULARY_SUCCESS_CODE(HttpStatus.OK,
            "VOCABULARY_200",
            "단어를 찾았습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
