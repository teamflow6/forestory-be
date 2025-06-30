package com.teamflow.forestory_be.story.chapter.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidChapterTitleException extends CustomException {

    private static final String ERROR_CODE = "CHAPTER_TITLE_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 회차 제목입니다.";

    public InvalidChapterTitleException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidChapterTitleException(String message) {
        super(ERROR_CODE, message);
    }
}
