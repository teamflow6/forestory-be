package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

import com.teamflow.forestory_be.story.series.presentation.dto.response.AuthorResponse;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterResponse;

public record GetChapterResponse(
        ChapterDetailResponse chapter,
        AuthorResponse author
) {
    public static GetChapterResponse of(ChapterDetailResponse chapter, AuthorResponse author) {
        return new GetChapterResponse(chapter, author);
    }
}
