package com.teamflow.forestory_be.likes.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetMyLikesResponse(
        List<LikeListItem> items,
        LocalDateTime nextCursorUpdatedAt,
        Long nextCursorLikeId,
        boolean hasNext
) {
}
