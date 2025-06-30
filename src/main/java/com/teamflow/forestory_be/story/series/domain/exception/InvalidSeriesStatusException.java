package com.teamflow.forestory_be.story.series.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidSeriesStatusException extends CustomException {

    private static final String ERROR_CODE = "SERIES_003";
    private static final String DEFAULT_MESSAGE = "존재하지 않은 시리즈 상태입니다.";

    public InvalidSeriesStatusException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidSeriesStatusException(String message) {
        super(ERROR_CODE, message);
    }
}
