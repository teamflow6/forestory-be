package com.teamflow.forestory_be.story.series.presentation.dto.response;

public record CompleteSeriesResponse(
        String seriesId,
        String status
) {
    public static CompleteSeriesResponse of(String seriesId, String status) {
        return new CompleteSeriesResponse(seriesId, status);
    }
}
