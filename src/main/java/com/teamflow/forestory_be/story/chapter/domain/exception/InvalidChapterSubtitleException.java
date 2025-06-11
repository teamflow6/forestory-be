package com.teamflow.forestory_be.story.chapter.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidChapterSubtitleException extends CustomException {

    private static final String ERROR_CODE = "CHAPTER_SUBTITLE_001";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 회차 부제목입니다.";

    public InvalidChapterSubtitleException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidChapterSubtitleException(String message) {
        super(ERROR_CODE, message);
    }
}
