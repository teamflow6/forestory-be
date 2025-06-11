package com.teamflow.forestory_be.story.series.presentation.dto.response;


public record CreateSeriesResponse(
        Long seriesId,
        String seriesTitle
) {
    public static CreateSeriesResponse of(Long articleId, String seriesTitle) {
        return new CreateSeriesResponse(articleId, seriesTitle);
    }
}
