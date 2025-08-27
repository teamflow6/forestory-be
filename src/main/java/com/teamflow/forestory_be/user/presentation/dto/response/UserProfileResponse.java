package com.teamflow.forestory_be.user.presentation.dto.response;

import com.teamflow.forestory_be.user.domain.entity.User;

public record UserProfileResponse(
    Long userId,
    String name,
    String introduction,
    String profileUrl,
    String contactUrl
) {

    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
            user.getId(),
            user.getName().value(),
            user.getIntroduction().value(),
            user.getProfileImageUrl().value(),
            user.getContactUrl().value()
        );
    }
}
