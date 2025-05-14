package com.teamflow.forestory_be.article.domain.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class ArticleNotFoundException extends CustomException {
    private static final String ERROR_CODE = "ARTICLE_001";
    private static final String DEFAULT_MESSAGE = "해당 아티클이 존재하지 않습니다.";

    public ArticleNotFoundException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }
}

