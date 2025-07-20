package com.teamflow.forestory_be.story.series.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class SeriesNotFoundException extends CustomException {
    private static final String ERROR_CODE = "SERIES_004";
    private static final String DEFAULT_MESSAGE = "해당 아티클이 존재하지 않습니다.";

    public SeriesNotFoundException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
