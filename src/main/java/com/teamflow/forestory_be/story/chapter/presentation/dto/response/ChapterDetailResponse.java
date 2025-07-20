package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.series.domain.entity.Series;

public record ChapterDetailResponse(
        Long chapterId,
        String chapterNumber,
        String chapterTitle,
        String chapterSubtitle,
        String body,
        Long seriesId,
        String seriesTitle
) {
    public static ChapterDetailResponse of(Chapter chapter, Series series) {
        return new ChapterDetailResponse(
                chapter.getId(),
                String.format("%02d", chapter.getChapterNumber()),  // 01, 02 등 형식 유지
                chapter.getTitle().value(),                                // 그냥 get으로 꺼냄
                chapter.getSubtitle().value(),                             // nullable 처리 없음
                chapter.getBody().value(),                                 // 마찬가지로 가공 없이 get
                series.getId(),
                series.getTitle().value()
        );
    }

}
