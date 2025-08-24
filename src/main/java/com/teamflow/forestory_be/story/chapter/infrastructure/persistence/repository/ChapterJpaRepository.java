package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    void deleteBySeriesId(Long seriesId);

    @Query("SELECT c FROM ChapterJpaEntity c WHERE c.seriesId = :seriesId ORDER BY c.chapterNumber DESC")
    ChapterJpaEntity findTopBySeriesIdOrderByChapterNumberDesc(Long seriesId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update ChapterJpaEntity c
              set c.likeCount = c.likeCount + 1
            where c.id = :id
           """)
    int increaseLikeCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update ChapterJpaEntity c
              set c.likeCount = case when c.likeCount > 0 then c.likeCount - 1 else 0 end
            where c.id = :id
           """)
    int decreaseLikeCount(@Param("id") Long id);
}
