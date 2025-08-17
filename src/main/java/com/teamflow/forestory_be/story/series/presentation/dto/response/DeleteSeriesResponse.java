package com.teamflow.forestory_be.story.series.presentation.dto.response;


public record DeleteSeriesResponse(
        String seriesId
) {
    public static DeleteSeriesResponse from(Long seriesId) {
        return new DeleteSeriesResponse(String.valueOf(seriesId));
    }
}
