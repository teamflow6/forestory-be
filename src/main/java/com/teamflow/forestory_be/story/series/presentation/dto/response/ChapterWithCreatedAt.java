package com.teamflow.forestory_be.story.series.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import java.time.LocalDateTime;

public record ChapterWithCreatedAt(
        Chapter chapter,
        LocalDateTime createdAt
) {
}
