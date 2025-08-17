package com.teamflow.forestory_be.story.series.application.dto.command;

public record DeleteSeriesCommand(
        Long seriesId,
        Long authorId
) {
}
