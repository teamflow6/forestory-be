package com.teamflow.forestory_be.article.domain.vo;

import com.teamflow.forestory_be.article.domain.exception.InvalidArticleStatusException;

public enum ArticleStatus {
    DRAFT, PUBLISHED;

    public static ArticleStatus from(String value) {
        try {
            return ArticleStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidArticleStatusException(value);
        }
    }
}
