package com.teamflow.forestory_be.auth.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class AlreadyExpiredTokenException extends CustomException {

    private static final String ERROR_CODE = "AUTH_001";
    private static final String DEFAULT_MESSAGE = "이미 만료된 토큰입니다: %s";

    public AlreadyExpiredTokenException(final String value) {
        super(ERROR_CODE, String.format(DEFAULT_MESSAGE, value));
    }
}
