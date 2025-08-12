package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

public record CreateChapterResponse(
        String chapterId,
        String seriesId,
        int chapterNumber
) {
    public static CreateChapterResponse of(Long chapterId, Long seriesId, int chapterNumber) {
        return new CreateChapterResponse(String.valueOf(chapterId), String.valueOf(seriesId), chapterNumber);
    }
}
