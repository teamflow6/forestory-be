package com.teamflow.forestory_be.user.presentation.dto.response;

import com.teamflow.forestory_be.user.domain.entity.User;

public record UserProfileResponse(
    String userId,
    String name,
    String introduction,
    String profileUrl,
    String contactUrl
) {

    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
            user.getId().toString(),
            user.getName().value(),
            user.getIntroduction().value(),
            user.getProfileImageUrl().value(),
            user.getContactUrl().value()
        );
    }
}
