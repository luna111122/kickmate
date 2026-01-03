package com.kickmate.kickmate.global.apiPayload.handler;

import com.kickmate.kickmate.global.apiPayload.ApiResponse;
import com.kickmate.kickmate.global.apiPayload.exception.GeneralException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {


    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<?>> handleException(
            GeneralException ex
    ) {
        return ResponseEntity.status(ex.getCode().getStatus())
                .body(ApiResponse.onFailure(
                        ex.getCode(),
                        null
                ));
    }
}
