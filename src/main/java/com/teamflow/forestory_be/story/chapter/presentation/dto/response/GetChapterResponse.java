package com.teamflow.forestory_be.story.chapter.presentation.dto.response;

import java.util.List;

public record GetChapterResponse(
        ChapterDetailResponse chapter,
        AuthorResponse author,
        List<NeighborChapter> aroundChapters,
        List<RandomAuthorContentResponse> randomPicks
) {
    public static GetChapterResponse of(ChapterDetailResponse chapter,
                                        AuthorResponse author,
                                        List<NeighborChapter> aroundChapters,
                                        List<RandomAuthorContentResponse> randomPicks) {
        return new GetChapterResponse(chapter, author, aroundChapters, randomPicks);
    }
}
