package com.teamflow.forestory_be.story.series.presentation.dto.response;

public record GetSeriesListResponse(
        String seriesId,
        String title,
        String introduction,
        String thumbnailUrl,
        String  createdAt,
        String type,
        String status
) {
}
