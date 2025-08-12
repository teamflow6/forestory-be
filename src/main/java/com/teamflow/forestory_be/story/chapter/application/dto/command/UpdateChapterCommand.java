package com.teamflow.forestory_be.story.chapter.application.dto.command;

import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import java.util.List;

public record UpdateChapterCommand(
        Long chapterId,
        Long seriesId,
        Long authorId,
        String title,
        String subtitle,
        String body,
        ChapterStatus status,
        int chapterNumber
) {
}
