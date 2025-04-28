package com.teamflow.forestory_be.article.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidContentException extends CustomException {

    private static final String ERROR_CODE = "CONTENT_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 내용입니다.";
    public InvalidContentException() {super(ERROR_CODE, DEFAULT_MESSAGE);}
    public InvalidContentException(String message) {super(ERROR_CODE, message);}
}
