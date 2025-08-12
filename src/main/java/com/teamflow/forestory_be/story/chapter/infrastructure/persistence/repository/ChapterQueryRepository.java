package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.chapter.presentation.dto.response.NeighborChapter;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.List;

public interface ChapterQueryRepository {
    List<ChapterWithCreatedAt> findChaptersBySeriesIdWithScroll(Long seriesId, String sort, int lastChapterNumber, int size);

    List<NeighborChapter> findAroundPublishedChapters(Long seriesId, int currentChapterNumber, int nextCount);
}

