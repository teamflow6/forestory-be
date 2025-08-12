package com.teamflow.forestory_be.story.series.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ChapterResponse(
        Long chapterId,
        int chapterNumber,
        String title,
        String subtitle,
        String publishedAt
) {
    public static ChapterResponse from(ChapterWithCreatedAt chapterWithCreatedAt) {
        Chapter chapter = chapterWithCreatedAt.chapter();
        LocalDateTime createdAt = chapterWithCreatedAt.createdAt();
        return new ChapterResponse(
                chapter.getId(),
                chapter.getChapterNumber(),
                chapter.getTitle().value(),
                chapter.getSubtitle().value(),
                createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        );
    }
}

