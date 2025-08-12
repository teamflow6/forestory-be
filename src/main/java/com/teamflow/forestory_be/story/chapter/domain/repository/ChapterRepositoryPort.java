package com.teamflow.forestory_be.story.chapter.domain.repository;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.NeighborChapter;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.List;

public interface ChapterRepositoryPort {
    void save(Chapter chapter);

    Integer findLastChapterNumber(Long seriesId);

    List<ChapterWithCreatedAt> findPublishedChaptersWithScroll(Long seriesId, String sort, int lastChapterNumber, int size);

    Chapter getById(Long id);

    List<NeighborChapter> findAroundPublishedChapters(Long seriesId, int currentChapterNumber,int nextCount);

    List<Chapter> findRandomPublishedByAuthor(Long authorId, Long excludeChapterId, int limit);
}
