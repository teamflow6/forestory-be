package com.teamflow.forestory_be.story.chapter.application.dto.command;

import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import java.util.List;

public record CreateChapterCommand(
        Long authorId,
        Long seriesId,
        String chapterTitle,
        String chapterSubtitle,
        String chapterBody,
        String thumbnailUrl,
        ChapterStatus chapterStatus
) {
}
