package com.teamflow.forestory_be.draft.infrastructure.repository;

import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface DraftReadPort {

    List<ArticleJpaEntity> sliceArticles(Long userId, LocalDateTime cursorCreatedAt, int limitPlusOne);

    // "NOVEL" 또는 "ESSAY"
    List<ChapterJpaEntity> sliceChaptersBySeriesType(Long userId, String type, LocalDateTime cursorCreatedAt, int limitPlusOne);
}
