package com.teamflow.forestory_be.user.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class UserNameDuplicatedException extends CustomException {

    private static final String ERROR_CODE = "USER_003";
    private static final String DEFAULT_MESSAGE = "이미 존재하는 유저 이름 입니다.";

    public UserNameDuplicatedException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
