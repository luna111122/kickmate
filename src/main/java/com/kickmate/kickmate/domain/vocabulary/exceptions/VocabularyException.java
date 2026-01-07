package com.kickmate.kickmate.domain.vocabulary.exceptions;

import com.kickmate.kickmate.global.apiPayload.code.BaseErrorCode;
import com.kickmate.kickmate.global.apiPayload.exception.GeneralException;

public class VocabularyException extends GeneralException {
    public VocabularyException(BaseErrorCode code) {
        super(code);
    }
}
