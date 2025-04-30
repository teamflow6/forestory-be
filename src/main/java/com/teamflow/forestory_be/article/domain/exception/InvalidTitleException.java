package com.teamflow.forestory_be.article.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidTitleException extends CustomException {

    private static final String ERROR_CODE = "TITLE_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 제목입니다.";

    public InvalidTitleException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidTitleException(String message) {
        super(ERROR_CODE, message);
    }
}
