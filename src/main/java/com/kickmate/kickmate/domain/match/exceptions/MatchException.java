package com.kickmate.kickmate.domain.match.exceptions;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import com.kickmate.kickmate.global.apiPayload.exception.GeneralException;

public class MatchException extends GeneralException {

    public MatchException(BaseErrorCode code) {
        super(code);
    }
}
