package com.teamflow.forestory_be.story.series.application.dto.command;

public record CompleteSeriesCommand(
        Long seriesId,
        Long authorId
) {}
