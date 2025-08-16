package com.teamflow.forestory_be.user.application.dto;

public record OnboardingUserCommand(
    Long userId,
    String name,
    String introduction,
    String profileImageUrl
) {
}
