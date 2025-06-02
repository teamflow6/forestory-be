package com.teamflow.forestory_be.article.presentation.dto.response;

import com.teamflow.forestory_be.article.domain.entity.Article;

public record UpdateArticleResponse(
        Long articleId
) {
    public static UpdateArticleResponse from(Long articleId) {
        return new UpdateArticleResponse(articleId);
    }
}
