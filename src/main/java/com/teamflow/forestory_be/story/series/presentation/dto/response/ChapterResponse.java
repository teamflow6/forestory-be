package com.teamflow.forestory_be.story.series.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.ChapterPersistenceMapper;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ChapterResponse(
        String chapterId,
        int chapterNumber,
        String title,
        String subtitle,
        String publishedAt
) {
    public static ChapterResponse from(ChapterWithCreatedAt chapterWithCreatedAt) {
        ChapterJpaEntity chapterEntity = chapterWithCreatedAt.chapter();
        LocalDateTime createdAt = chapterWithCreatedAt.createdAt();

        // JPA → Domain 변환
        Chapter chapter = ChapterPersistenceMapper.toDomainEntity(chapterEntity);

        return new ChapterResponse(
                chapter.getId().toString(),
                chapter.getChapterNumber(),
                chapter.getTitle().value(),
                chapter.getSubtitle().value(),
                createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        );
    }
}
