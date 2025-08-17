package com.teamflow.forestory_be.story.series.application.dto.command;

public record DeleteLatestChapterCommand(
        Long seriesId,
        Long authorId
) {
}
