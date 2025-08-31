package com.teamflow.forestory_be.comment.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidCommentWriterException extends CustomException {

    private static final String ERROR_CODE = "COMMENT_001";
    private static final String DEFAULT_MESSAGE = "권한이 없는 댓글 작성자입니다.";

    public InvalidCommentWriterException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
