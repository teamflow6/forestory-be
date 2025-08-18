package com.teamflow.forestory_be.story.series.application.dto.query;

public record GetNextChapterInfoQuery(
        Long userId,
        Long seriesId
) {}
