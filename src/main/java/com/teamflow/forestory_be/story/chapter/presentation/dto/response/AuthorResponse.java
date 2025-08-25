package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

import com.teamflow.forestory_be.user.domain.entity.User;

public record AuthorResponse(
    String authorId,
    String name,
    String introduction,
    String profileImageUrl
) {
    public static AuthorResponse from(User user) {
        return new AuthorResponse(
            String.valueOf(user.getId()),
            user.getName().value(),
            user.getIntroduction() != null ? user.getIntroduction().value() : null,
            user.getProfileImageUrl().value()
        );
    }
}
