package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

public record DeleteChapterResponse(
        String chapterId
) {
    public static DeleteChapterResponse of(Long chapterId) {
        return new DeleteChapterResponse(
                String.valueOf(chapterId)
        );
    }
}
