package com.kickmate.kickmate.domain.commentary.exception;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import com.kickmate.kickmate.global.apiPayload.exception.GeneralException;

public class CommentaryException extends GeneralException {

    public CommentaryException(BaseErrorCode code) {
        super(code);
    }
}
