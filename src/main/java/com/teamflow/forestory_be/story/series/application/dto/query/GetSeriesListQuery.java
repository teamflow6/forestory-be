package com.teamflow.forestory_be.story.series.application.dto.query;

public record GetSeriesListQuery(
        Long userId,
        Integer lastSeriesNumber,
        int size,
        String type
) {
}
