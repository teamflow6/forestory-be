package com.teamflow.forestory_be.article.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidArticleOwnerException extends CustomException {

    private static final String ERROR_CODE = "ARTICLE_002";
    private static final String DEFAULT_MESSAGE = "아티클 작성자가 아닙니다.";

    public InvalidArticleOwnerException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidArticleOwnerException(String message) {
        super(ERROR_CODE, message);
    }
}
