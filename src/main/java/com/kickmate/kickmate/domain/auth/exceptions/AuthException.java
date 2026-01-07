package com.kickmate.kickmate.domain.auth.exceptions;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import com.kickmate.kickmate.global.apiPayload.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(BaseErrorCode code) {
        super(code);
    }
}

