package com.teamflow.forestory_be.story.series.presentation.dto.response;

import com.teamflow.forestory_be.story.chapter.presentation.dto.response.AuthorResponse;
import com.teamflow.forestory_be.story.series.domain.entity.Series;
import com.teamflow.forestory_be.user.domain.entity.User;
import java.util.List;

public record GetSeriesResponse(
        String seriesId,
        String title,
        String introduction,
        String thumbnailUrl,
        String status,
        int totalPublishedChapters,
        List<ChapterResponse> chapter,
        AuthorResponse author
) {
    public static GetSeriesResponse of(
            Series series,
            List<ChapterResponse> publishedChapterResponses,
            User author
    ) {
        return new GetSeriesResponse(
                String.valueOf(series.getId()),
                series.getTitle().value(),
                series.getIntroduction().value(),
                series.getThumbnailUrl(),
                series.getSeriesStatus().name(),
                publishedChapterResponses.size(),
                publishedChapterResponses,
                AuthorResponse.from(author)
        );
    }

}

