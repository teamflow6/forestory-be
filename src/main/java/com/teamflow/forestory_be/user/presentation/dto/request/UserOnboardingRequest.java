package com.teamflow.forestory_be.user.presentation.dto.request;

public record UserOnboardingRequest(
    String name,
    String introduction,
    String profileUrl
) {
}
