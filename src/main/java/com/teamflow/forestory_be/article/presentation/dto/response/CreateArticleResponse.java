package com.teamflow.forestory_be.article.presentation.dto.response;

public record CreateArticleResponse(
        String articleId
) {
    public static CreateArticleResponse createFromId(Long articleId) {
        return new CreateArticleResponse(String.valueOf(articleId));
    }
    
}

