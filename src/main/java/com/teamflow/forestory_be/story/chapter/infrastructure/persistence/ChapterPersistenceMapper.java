package com.teamflow.forestory_be.story.chapter.infrastructure.persistence;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterBody;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterSubtitle;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterTitle;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;

public class ChapterPersistenceMapper {

    private ChapterPersistenceMapper() {
    }

    public static Chapter toDomainEntity(ChapterJpaEntity entity) {
        return Chapter.reconstruct(
                entity.getId(),
                entity.getSeriesId(),
                entity.getAuthorId(),
                new ChapterTitle(entity.getTitle()),
                new ChapterSubtitle(entity.getSubtitle()),
                new ChapterBody(entity.getBody()),
                entity.getStatus(),
                entity.getChapterNumber(),     // int
                entity.getPublishedAt()        // LocalDate (nullable)
        );
    }

    public static ChapterJpaEntity toJpaEntity(Chapter chapter) {
        return ChapterJpaEntity.builder()
                .id(chapter.getId())
                .seriesId(chapter.getSeriesId())
                .authorId(chapter.getAuthorId())
                .title(chapter.getTitle().value())
                .subtitle(chapter.getSubtitle().value())
                .body(chapter.getBody().value())
                .status(chapter.getStatus())
                .chapterNumber(chapter.getChapterNumber())
                .build();
    }
}
