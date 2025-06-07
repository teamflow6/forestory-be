package com.teamflow.forestory_be.article.presentation.dto.response;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.vo.ArticleStatus;

public record UpdateArticleStatusResponse(
        Long articleId,
        ArticleStatus newStatus
) {
    public static UpdateArticleStatusResponse from(Article article) {
        return new UpdateArticleStatusResponse(article.getId(), article.getStatus());
    }
}