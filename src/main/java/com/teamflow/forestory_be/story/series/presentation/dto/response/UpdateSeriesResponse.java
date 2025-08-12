package com.teamflow.forestory_be.story.series.presentation.dto.response;

public record UpdateSeriesResponse(
        String seriesId
) {
    public static UpdateSeriesResponse from(Long seriesId) { return new UpdateSeriesResponse(String.valueOf(seriesId)); }
}

