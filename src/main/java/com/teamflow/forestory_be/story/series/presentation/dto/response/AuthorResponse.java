package com.teamflow.forestory_be.story.series.presentation.dto.response;

import com.teamflow.forestory_be.user.domain.entity.User;

public record AuthorResponse(
        Long authorId,
        String name,
        String introduction,
        String profileImageUrl
) {
    public static AuthorResponse from(User user) {
        return new AuthorResponse(
                user.getId(),
                user.getName().value(),
                "", //추후 리팩토링해야함
                user.getProfileImageUrl()
        );
    }
}
