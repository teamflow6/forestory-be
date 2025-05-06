package com.teamflow.forestory_be.auth.application.dto;

public record ReissueTokenCommand(
    Long userId,
    Long tokenId
) {
}
