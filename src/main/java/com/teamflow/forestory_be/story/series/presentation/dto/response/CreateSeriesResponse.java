package com.teamflow.forestory_be.story.series.presentation.dto.response;


public record CreateSeriesResponse(
        String seriesId,
        String seriesTitle
) {
    public static CreateSeriesResponse of(Long seriesId, String seriesTitle) {
        return new CreateSeriesResponse(String.valueOf(seriesId), seriesTitle);
    }
}
