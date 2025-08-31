package com.teamflow.forestory_be.comment.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class CommentNotFoundException extends CustomException {

    private static final String ERROR_CODE = "COMMENT_003";
    private static final String DEFAULT_MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFoundException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}
