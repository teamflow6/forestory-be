package com.teamflow.forestory_be.story.series.application.dto.command;

import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.Type;

public record UpdateSeriesCommand(
        Long seriesId,
        Long authorId,
        String title,
        String introduction,
        String thumbnailUrl,
        Type type,
        SeriesStatus seriesStatus
) {}