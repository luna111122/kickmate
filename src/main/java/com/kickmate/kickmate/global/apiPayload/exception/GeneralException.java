package com.kickmate.kickmate.global.apiPayload.exception;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{

    private final BaseErrorCode code;
}
