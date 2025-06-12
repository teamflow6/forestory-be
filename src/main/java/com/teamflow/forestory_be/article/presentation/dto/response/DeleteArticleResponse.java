package com.teamflow.forestory_be.article.presentation.dto.response;

public record DeleteArticleResponse(
        Long articleId
) {
    public static DeleteArticleResponse from(Long articleId) {
        return new DeleteArticleResponse(articleId);
    }
}


