package com.teamflow.forestory_be.user.application.dto;

public record CreateUserCommand(
    String name,
    String profileImageUrl
) {
}
