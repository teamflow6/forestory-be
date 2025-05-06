package com.teamflow.forestory_be.auth.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class NotSupportSocialTypeException extends CustomException {

    private static final String ERROR_CODE = "AUTH_004";
    private static final String DEFAULT_MESSAGE = "지원하지 않는 소셜 타입입니다: %s";

    public NotSupportSocialTypeException(final String value) {
        super(ERROR_CODE, String.format(DEFAULT_MESSAGE, value));
    }
}
