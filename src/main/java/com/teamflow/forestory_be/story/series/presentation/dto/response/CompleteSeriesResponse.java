package com.teamflow.forestory_be.story.series.presentation.dto.response;

public record CompleteSeriesResponse(
        Long seriesId,
        String status
) {
    public static CompleteSeriesResponse of(Long seriesId, String status) {
        return new CompleteSeriesResponse(seriesId, status);
    }
}
