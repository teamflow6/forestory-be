package com.teamflow.forestory_be.user.domain.vo;

public record ProfileImageUrl(
    String value
) {
    public static ProfileImageUrl empty() {
        return new ProfileImageUrl("");
    }

    public static ProfileImageUrl fromNullable(String newValue, ProfileImageUrl value) {
        if (newValue == null || newValue.isEmpty()) {
            return value;
        }
        return new ProfileImageUrl(newValue);
    }
}
