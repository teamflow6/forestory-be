package com.teamflow.forestory_be.article.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class InvalidArticleStatusException extends CustomException {

    private static final String ERROR_CODE = "ARTICLE_003";
    private static final String DEFAULT_MESSAGE = "존재하지 않은 아티클 상태입니다.";

    public InvalidArticleStatusException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public InvalidArticleStatusException(String message) {
        super(ERROR_CODE, message);
    }
}
