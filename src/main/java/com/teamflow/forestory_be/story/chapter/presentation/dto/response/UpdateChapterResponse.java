package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

public record UpdateChapterResponse(
        Long chapterId,
        Long seriesId,
        String chapterNumber
) {
    public static UpdateChapterResponse of(Long chapterId, Long seriesId, String chapterNumber) {
        return new UpdateChapterResponse(chapterId, seriesId, chapterNumber);
    }
}
