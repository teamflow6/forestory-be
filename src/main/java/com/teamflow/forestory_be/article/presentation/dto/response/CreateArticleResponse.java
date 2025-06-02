package com.teamflow.forestory_be.article.presentation.dto.response;

public record CreateArticleResponse(
        Long articleId
) {
    public static CreateArticleResponse createFromId(Long articleId) {
        return new CreateArticleResponse(articleId);
    }

    public static CreateArticleResponse draftFromId(Long articleId) {
        return new CreateArticleResponse(articleId);
    }
}

