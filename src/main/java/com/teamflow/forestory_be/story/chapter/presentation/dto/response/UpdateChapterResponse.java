package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

public record UpdateChapterResponse(
        String chapterId,
        String seriesId,
        int chapterNumber
) {
    public static UpdateChapterResponse of(Long chapterId, Long seriesId, int chapterNumber) {
        return new UpdateChapterResponse(String.valueOf(chapterId), String.valueOf(seriesId), chapterNumber);
    }
}
