package com.teamflow.forestory_be.auth.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidTokenException extends CustomException {

    private static final String ERROR_CODE = "AUTH_002";
    private static final String DEFAULT_MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidTokenException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
