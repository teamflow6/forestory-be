package com.teamflow.forestory_be.story.chapter.application.dto.command;

public record DeleteChapterCommand(
        Long chapterId,
        Long userId
) {}
