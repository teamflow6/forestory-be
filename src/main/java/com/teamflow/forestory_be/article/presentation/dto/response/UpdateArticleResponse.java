package com.teamflow.forestory_be.article.presentation.dto.response;

public record UpdateArticleResponse(
        String articleId
) {
    public static UpdateArticleResponse from(Long articleId) {
        return new UpdateArticleResponse(String.valueOf(articleId));
    }
}
