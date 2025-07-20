package com.teamflow.forestory_be.story.series.application.dto.query;

public record GetSeriesQuery(
        Long seriesId,
        String sort,
        Integer lastChapterNumber,
        int size
) {
}
