package com.teamflow.forestory_be.likes.presentation.dto.request;

import java.time.LocalDateTime;

public record GetLikeRequest(
        Integer size,
        LocalDateTime cursorUpdatedAt,
        Long cursorLikeId
) {
}
