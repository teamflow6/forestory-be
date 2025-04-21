package com.teamflow.forestory_be.user.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class UserNotFoundException extends CustomException {

    private static final String ERROR_CODE = "USER_001";
    private static final String DEFAULT_MESSAGE = "존재하지 않는 유저입니다";

    public UserNotFoundException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
