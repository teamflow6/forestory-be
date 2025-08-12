package com.teamflow.forestory_be.article.presentation.dto.response;

public record DeleteArticleResponse(
        String articleId
) {
    public static DeleteArticleResponse from(Long articleId) {
        return new DeleteArticleResponse(String.valueOf(articleId));
    }
}


