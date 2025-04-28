package com.teamflow.forestory_be.article.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidSubtitleException extends CustomException {

    private static final String ERROR_CODE = "SUBTITLE_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 부제목입니다.";
    public InvalidSubtitleException() {super(ERROR_CODE, DEFAULT_MESSAGE);}
    public InvalidSubtitleException(String message) {super(ERROR_CODE, message);}
}
