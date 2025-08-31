package com.teamflow.forestory_be.comment.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidCommentContentException extends CustomException {

    private static final String ERROR_CODE = "COMMENT_005";
    private static final String DEFAULT_MESSAGE = "올바르지 않은 형식의 댓글입니다.";

    public InvalidCommentContentException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidCommentContentException(String message) {
        super(ERROR_CODE, message);
    }
}
