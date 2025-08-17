package com.teamflow.forestory_be.story.series.presentation.dto.response;

import lombok.Builder;

@Builder
public record DeleteChapterResponse(
        Long deletedChapterId,
        Long seriesId
) {
    public static DeleteChapterResponse of(Long deletedChapterId, Long seriesId) {
        return new DeleteChapterResponse(deletedChapterId, seriesId);
    }
}
