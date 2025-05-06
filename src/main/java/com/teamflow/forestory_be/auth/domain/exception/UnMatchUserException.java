package com.teamflow.forestory_be.auth.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class UnMatchUserException extends CustomException {

    private static final String ERROR_CODE = "AUTH_003";
    private static final String DEFAULT_MESSAGE = "올바르지 않는 토큰을 가진 유저입니다: %s";

    public UnMatchUserException(final String value) {
        super(ERROR_CODE, String.format(DEFAULT_MESSAGE, value));
    }
}
