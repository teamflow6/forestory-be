package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.List;

public interface ChapterQueryRepository {
    List<ChapterWithCreatedAt> findChaptersBySeriesIdWithScroll(Long seriesId, String sort, String lastChapterNumber,
                                                                int size);
}

