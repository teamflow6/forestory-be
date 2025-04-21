package com.teamflow.forestory_be.user.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidUserNameException extends CustomException {

    private static final String ERROR_CODE = "USER_002";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 유저 이름입니다.";

    public InvalidUserNameException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidUserNameException(String message) {
        super(ERROR_CODE, message);
    }
}
