package com.teamflow.forestory_be.story.series.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidSeriesOwnerException extends CustomException {

    private static final String ERROR_CODE = "SERIES_002";
    private static final String DEFAULT_MESSAGE = "시리즈 작성자가 아닙니다.";

    public InvalidSeriesOwnerException() {super(ERROR_CODE, DEFAULT_MESSAGE);}

    public InvalidSeriesOwnerException(String message) {
        super(ERROR_CODE, message);
    }
}
