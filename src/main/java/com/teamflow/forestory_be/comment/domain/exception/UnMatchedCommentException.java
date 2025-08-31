package com.teamflow.forestory_be.comment.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class UnMatchedCommentException extends CustomException {

    private static final String ERROR_CODE = "COMMENT_004";
    private static final String DEFAULT_MESSAGE = "해당 게시글의 댓글이 아닙니다.";

    public UnMatchedCommentException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
