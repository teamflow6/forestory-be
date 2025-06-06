package com.teamflow.forestory_be.auth.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class EmailNotVerifiedException extends CustomException {

    private static final String ERROR_CODE = "AUTH_005";
    private static final String DEFAULT_MESSAGE = "검증되지 않은 이메일입니다";

    public EmailNotVerifiedException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
