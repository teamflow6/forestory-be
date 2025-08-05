package com.teamflow.forestory_be.story.chapter.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidChapterOwnerException extends CustomException {

    private static final String ERROR_CODE = "CHAPTER_002";
    private static final String DEFAULT_MESSAGE = "챕터 작성자가 아닙니다.";

    public InvalidChapterOwnerException() {super(ERROR_CODE, DEFAULT_MESSAGE);}

    public InvalidChapterOwnerException(String message) {
        super(ERROR_CODE,message);
    }
}
