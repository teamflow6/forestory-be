package com.teamflow.forestory_be.story.series.application.dto.command;

import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;

public record CreateSeriesCommand(
        Long authorId,
        String seriesTitle,
        String introduction,
        String thumbnailUrl,
        String type,
        SeriesStatus status

) {
}
