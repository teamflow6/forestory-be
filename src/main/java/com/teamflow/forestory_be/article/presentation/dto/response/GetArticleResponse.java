package com.teamflow.forestory_be.article.presentation.dto.response;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.user.domain.entity.User;

public record GetArticleResponse(

        Long articleId,
        String title,
        String subtitle,
        String content,
        String thumbnailUrl,
        ArticleAuthorResponse author

) {
    public static GetArticleResponse Of(Article article, User author) {
        return new GetArticleResponse(
                article.getId(),
                article.getTitle().value(),
                article.getSubtitle().value(),
                article.getContent().value(),
                article.getThumbnailUrl(),
                ArticleAuthorResponse.from(author)
        );
    }
}
