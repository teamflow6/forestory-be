package com.teamflow.forestory_be.article.presentation.dto.response;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.user.domain.entity.User;

public record SelectArticleResponse(

        Long articleId,
        String title,
        String subtitle,
        String content,
        String thumbnailUrl,
        Long authorId,
        String authorName,
        String profileImageUrl,
        String message

) {
    public static SelectArticleResponse selectOf(Article article, User author) {
        return new SelectArticleResponse(
                article.getId(),
                article.getTitle().value(),
                article.getSubtitle().value(),
                article.getContent().value(),
                article.getThumbnailUrl(),
                author.getId(),
                author.getName().value(),
                author.getProfileImageUrl(),
                "아티클이 성공적으로 조회되었습니다."
        );
    }
}
