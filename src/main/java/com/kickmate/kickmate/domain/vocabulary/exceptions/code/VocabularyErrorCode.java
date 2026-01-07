package com.kickmate.kickmate.domain.vocabulary.exceptions.code;


import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VocabularyErrorCode implements BaseErrorCode {


    VOCABULARY_ERROR_CODE(HttpStatus.BAD_REQUEST,
            "VOCABULARY_400",
            "단어를 찾지 못했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
