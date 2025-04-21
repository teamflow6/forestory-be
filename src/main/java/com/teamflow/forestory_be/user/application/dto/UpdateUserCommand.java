package com.teamflow.forestory_be.user.application.dto;

public record UpdateUserCommand(
    Long userId,
    String name,
    String profileImageUrl
) {
}
