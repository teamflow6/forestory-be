package com.teamflow.forestory_be.article.web.response;

public record CreateArticleResponse(
    Long articleId,
    String message
) {
    public static CreateArticleResponse of(Long articleId) {
        return new CreateArticleResponse(articleId, "아티클이 성공적으로 생성되었습니다.");
    }
}
