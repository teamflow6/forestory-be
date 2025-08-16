package com.teamflow.forestory_be.user.presentation.dto.request;

public record UpdateProfileRequest(
    String name,
    String introduction,
    String profileUrl
) {
}
