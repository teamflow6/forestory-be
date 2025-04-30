package com.teamflow.forestory_be.article.presentation.response;

public record CreateArticleResponse(
        Long articleId,
        String message
) {
    public static CreateArticleResponse createFromId(Long articleId) {
        return new CreateArticleResponse(articleId, "아티클이 성공적으로 생성되었습니다.");
    }

    public static CreateArticleResponse draftFromId(Long articleId) {
        return new CreateArticleResponse(articleId, "아티클이 임시 저장되었습니다.");
    }
}
