package com.teamflow.forestory_be.likes.application.dto.query;

import java.time.LocalDateTime;

public record GetMyLikesQuery(
        Long userId,
        int size,
        LocalDateTime cursorUpdatedAt, // null이면 첫 페이지
        Long cursorLikeId
) {
}
