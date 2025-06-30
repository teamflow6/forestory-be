package com.teamflow.forestory_be.story.series.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidTypeException extends CustomException {

    private static final String ERROR_CODE = "SERIES_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 타입입니다.";

    public InvalidTypeException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidTypeException(String message) {
        super(ERROR_CODE, message);
    }
}
