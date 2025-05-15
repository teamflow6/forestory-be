package com.teamflow.forestory_be.article.presentation.dto.response;

import com.teamflow.forestory_be.user.domain.entity.User;

public record ArticleAuthorResponse(
        Long id,
        String name,
        String profileImageUrl
) {
    public static ArticleAuthorResponse from(User author) {
        return new ArticleAuthorResponse(
                author.getId(),
                author.getName().value(),
                author.getProfileImageUrl()
        );
    }
}
