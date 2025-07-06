package com.teamflow.forestory_be.story.chapter.domain.repository;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.List;

public interface ChapterRepositoryPort {
    void save(Chapter chapter);

    String findLastChapterNumber(Long seriesId);

    List<ChapterWithCreatedAt> findPublishedChaptersWithScroll(Long seriesId, String sort, String lastChapterNumber,
                                                               int size);

    Chapter getById(Long id);
}
