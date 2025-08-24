package com.teamflow.forestory_be.story.chapter.domain.repository;

import com.teamflow.forestory_be.story.chapter.domain.entity.Chapter;
import com.teamflow.forestory_be.story.chapter.presentation.dto.response.NeighborChapter;
import com.teamflow.forestory_be.story.series.presentation.dto.response.ChapterWithCreatedAt;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChapterRepositoryPort {
    void save(Chapter chapter);

    Integer findLastChapterNumber(Long seriesId);

    List<ChapterWithCreatedAt> findPublishedChaptersWithScroll(Long seriesId, String sort, int lastChapterNumber, int size);

    Chapter getById(Long id);

    List<NeighborChapter> findAroundPublishedChapters(Long seriesId, int currentChapterNumber,int nextCount);

    List<Chapter> findRandomPublishedByAuthor(Long authorId, Long excludeChapterId, int limit);

    void deleteAllBySeriesId(Long seriesId);

    Chapter findTopBySeriesIdOrderByChapterNumberDesc(Long seriesId);

    void delete(Chapter chapter);

    void increaseLikeCount(Long articleId);

    void decreaseLikeCount(Long articleId);
}
