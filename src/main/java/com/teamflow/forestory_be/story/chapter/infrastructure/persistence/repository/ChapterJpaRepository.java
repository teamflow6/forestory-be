package com.teamflow.forestory_be.story.chapter.infrastructure.persistence.repository;

import com.teamflow.forestory_be.story.chapter.domain.vo.ChapterStatus;
import com.teamflow.forestory_be.story.chapter.infrastructure.persistence.entity.ChapterJpaEntity;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
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

    ChapterJpaEntity findTopBySeriesIdOrderByChapterNumberDescIdDesc(Long seriesId);

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

    @Query("""
    select coalesce(sum(c.likeCount), 0)
    from ChapterJpaEntity c
    where c.seriesId = :seriesId
      and c.status = :status
    """)
    Long sumLikeCountBySeriesAndStatus(@Param("seriesId") Long seriesId,
                                       @Param("status") ChapterStatus status);

    @Query(value = """
    SELECT c.*
    FROM chapters c
    JOIN series s ON s.series_id = c.series_id
    WHERE c.author_id = :userId
      AND c.status = 'DRAFT'
      AND (:cursor IS NULL OR c.created_at < :cursor)
      AND s.type = :type            -- 'NOVEL' 또는 'ESSAY'
    ORDER BY c.created_at DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<ChapterJpaEntity> sliceChaptersBySeriesType(
            @Param("userId") Long userId,
            @Param("cursor") LocalDateTime cursor,
            @Param("type") String type,   // "NOVEL" or "ESSAY"
            @Param("limit") int limit     // size+1 전달
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
    DELETE FROM ChapterJpaEntity c
    WHERE c.authorId = :userId
      AND c.seriesId IN (
          SELECT s.id FROM SeriesJpaEntity s
          WHERE s.type = :seriesType
      )
      AND c.id IN :targetIds
""")
    int deleteChaptersBySeriesType(Long userId, Type seriesType, Collection<Long> targetIds);

    @Query("SELECT c FROM ChapterJpaEntity c WHERE c.id IN :ids")
    List<ChapterJpaEntity> findAllByIdIn(@Param("ids") List<Long> ids);
}

