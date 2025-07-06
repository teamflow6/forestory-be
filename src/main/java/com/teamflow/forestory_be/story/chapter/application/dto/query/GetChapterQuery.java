package com.teamflow.forestory_be.story.chapter.application.dto.query;

public record GetChapterQuery(
        Long chapterId,
        Long userId
) {
}
