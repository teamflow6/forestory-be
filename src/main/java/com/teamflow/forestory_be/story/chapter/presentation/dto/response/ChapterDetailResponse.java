package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.series.domain.entity.Series;

public record ChapterDetailResponse(
        String chapterId,
        int chapterNumber,
        String chapterTitle,
        String chapterSubtitle,
        String body,
        String seriesId,
        String seriesTitle,
        long likeCount
) {
    public static ChapterDetailResponse of(Chapter chapter, Series series) {
        return new ChapterDetailResponse(
                String.valueOf(chapter.getId()),
                chapter.getChapterNumber(),
                chapter.getTitle().value(),
                chapter.getSubtitle().value(),
                chapter.getBody().value(),
                series.getId().toString(),
                series.getTitle().value(),
                chapter.getLikeCount()
        );
    }
}
