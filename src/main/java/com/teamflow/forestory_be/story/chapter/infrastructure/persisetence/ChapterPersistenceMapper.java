package com.teamflow.forestory_be.story.chapter.infrastructure.persisetence;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterBody;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterSubtitle;
import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterTitle;
import com.teamflow.forestory_be.story.chapter.infrastructure.persisetence.entity.ChapterJpaEntity;

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
                entity.getImageUrls(),
                entity.getStatus(),
                entity.getChapterNumber()
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
                .imageUrls(chapter.getImageUrls())
                .status(chapter.getStatus())
                .chapterNumber(chapter.getChapterNumber())
                .build();
    }
}
