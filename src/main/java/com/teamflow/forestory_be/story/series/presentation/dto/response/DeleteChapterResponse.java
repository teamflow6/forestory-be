package com.teamflow.forestory_be.story.series.presentation.dto.response;

import lombok.Builder;

@Builder
public record DeleteChapterResponse(
        String deletedChapterId,
        String seriesId
) {
    public static DeleteChapterResponse of(String deletedChapterId, String seriesId) {
        return new DeleteChapterResponse(deletedChapterId, seriesId);
    }
}
