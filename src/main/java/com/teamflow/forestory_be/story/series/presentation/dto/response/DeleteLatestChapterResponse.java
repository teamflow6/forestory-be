package com.teamflow.forestory_be.story.series.presentation.dto.response;

import lombok.Builder;

@Builder
public record DeleteLatestChapterResponse(
        String deletedChapterId,
        String seriesId
) {
    public static DeleteLatestChapterResponse of(String deletedChapterId, String seriesId) {
        return new DeleteLatestChapterResponse(deletedChapterId, seriesId);
    }
}
