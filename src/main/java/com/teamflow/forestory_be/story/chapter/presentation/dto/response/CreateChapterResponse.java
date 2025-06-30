package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;

public record CreateChapterResponse(
        Long chapterId,
        Long seriesId,
        String chapterNumber
) {
    public static CreateChapterResponse of(Long chapterId, Long seriesId, String chapterNumber) {
        return new CreateChapterResponse(chapterId, seriesId, chapterNumber);
    }
}
