package com.teamflow.forestory_be.story.series.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidIntroductionException extends CustomException {

    private static final String ERROR_CODE = "SERIES_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 작품 소개입니다.";

    public InvalidIntroductionException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidIntroductionException(String message) {
        super(ERROR_CODE, message);
    }
}
