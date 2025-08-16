package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChapterJpaRepository extends JpaRepository<ChapterJpaEntity, Long>, ChapterQueryRepository {

    @Query("select max(c.chapterNumber) from ChapterJpaEntity c where c.seriesId = :seriesId")
    Integer findMaxChapterNumber(Long seriesId);

    @Query(value = """
        SELECT * FROM chapters
        WHERE author_id = :authorId
          AND status = 'PUBLISHED'
          AND (:excludeId IS NULL OR chapters.chapter_id <> :excludeId)
        ORDER BY RAND()
        LIMIT :limit
        """, nativeQuery = true)
    List<ChapterJpaEntity> pickRandomByAuthor(Long authorId, Long excludeId, int limit);
}
