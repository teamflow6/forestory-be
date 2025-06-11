package com.teamflow.forestory_be.story.chapter.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidChapterStatusException extends CustomException {

    private static final String ERROR_CODE = "CHAPTER_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 챕터 상태입니다. ";

    public InvalidChapterStatusException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidChapterStatusException(String message) {
        super(ERROR_CODE, message);
    }
}
