package com.teamflow.forestory_be.story.series.application.dto.query;

import java.time.LocalDateTime;

public record GetSeriesListQuery(
        Long userId,
        LocalDateTime lastCreatedAt,
        int size,
        String type
) {
}
