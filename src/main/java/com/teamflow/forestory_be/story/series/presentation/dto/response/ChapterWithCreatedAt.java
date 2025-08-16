package com.teamflow.forestory_be.story.series.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import java.time.LocalDateTime;

public record ChapterWithCreatedAt(
        ChapterJpaEntity chapter,
        LocalDateTime createdAt
) {
}
