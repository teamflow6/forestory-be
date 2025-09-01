package com.teamflow.forestory_be.story.series.infrastructure.persistence.repository;

import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import com.teamflow.forestory_be.story.series.domain.vo.SeriesStatus;
import com.teamflow.forestory_be.story.series.domain.vo.Type;
import com.teamflow.forestory_be.story.series.infrastructure.persistence.entity.SeriesJpaEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeriesJpaRepository extends JpaRepository<SeriesJpaEntity, Long> {

    @Query("""

            SELECT s 
        FROM SeriesJpaEntity s
        WHERE s.authorId = :userId
        AND (:lastSeriesNumber IS NULL OR s.id < :lastSeriesNumber)
        ORDER BY s.id DESC
        """)
    List<SeriesJpaEntity> findByAuthorId(
            @Param("userId") Long userId,
            @Param("lastSeriesNumber") Integer lastSeriesNumber,
            Pageable pageable
    );

    @Query("""
    SELECT s
    FROM SeriesJpaEntity s
    WHERE s.authorId = :authorId
      AND s.seriesStatus = :status
      AND (:lastCreatedAt IS NULL OR s.createdAt < :lastCreatedAt)
    ORDER BY s.createdAt DESC
""")
    List<SeriesJpaEntity> findByAuthorAndStatus(
            @Param("authorId") Long authorId,
            @Param("status") SeriesStatus status,
            @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
            Pageable pageable
    );

    @Query("""
    SELECT s
    FROM SeriesJpaEntity s
    WHERE s.authorId = :authorId
      AND s.type = :type
      AND s.seriesStatus = :status
      AND (:lastCreatedAt IS NULL OR s.createdAt < :lastCreatedAt)
    ORDER BY s.createdAt DESC
""")
    List<SeriesJpaEntity> findByAuthorAndTypeAndStatus(
            @Param("authorId") Long authorId,
            @Param("type") Type type,
            @Param("status") SeriesStatus status,
            @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
            Pageable pageable
    );



    @Query("""
        SELECT s.id, s.title, s.thumbnailUrl, COUNT(l.id)
          FROM SeriesJpaEntity s
          LEFT JOIN LikeJpaEntity l
                 ON l.targetId = s.id
                AND l.targetType = :likeTargetType 
         WHERE s.type = :seriesType                 
         GROUP BY s.id, s.title, s.thumbnailUrl
         ORDER BY COUNT(l.id) DESC
    """)
    List<Object[]> findTopByLikeCount(@Param("seriesType") Type seriesType,
                                      @Param("likeTargetType") TargetType likeTargetType,
                                      Pageable pageable);

    /* 홈 섹션: 주간 좋아요 Top N (type별) */
    @Query("""
        SELECT s.id, s.title, s.thumbnailUrl, COUNT(l.id)
        FROM SeriesJpaEntity s
        LEFT JOIN LikeJpaEntity l
               ON l.targetId = s.id
              AND l.targetType = :likeType
              AND l.createdAt >= :since AND l.createdAt < :until
        WHERE s.type = :seriesType
        GROUP BY s.id, s.title, s.thumbnailUrl
        ORDER BY COUNT(l.id) DESC, s.id DESC
    """)
    List<Object[]> topByWeeklyLikes(@Param("seriesType") Type seriesType,
                                    @Param("likeType") TargetType likeType,
                                    @Param("since") LocalDateTime since,
                                    @Param("until") LocalDateTime until,
                                    Pageable pageable);

    /* 인기: 첫 페이지 */
    @Query("""
        SELECT s.id, s.title, s.thumbnailUrl, COUNT(l.id) AS wl
        FROM SeriesJpaEntity s
        LEFT JOIN LikeJpaEntity l
               ON l.targetId = s.id
              AND l.targetType = :likeType
              AND l.createdAt >= :since AND l.createdAt < :until
        WHERE s.type = :seriesType
        GROUP BY s.id, s.title, s.thumbnailUrl
        ORDER BY wl DESC, s.id DESC
    """)
    List<Object[]> weeklyPopularFirst(@Param("seriesType") Type seriesType,
                                      @Param("likeType") TargetType likeType,
                                      @Param("since") LocalDateTime since,
                                      @Param("until") LocalDateTime until,
                                      Pageable pageable);

    /* 인기: 커서 이후 */
    @Query("""
        SELECT s.id, s.title, s.thumbnailUrl, COUNT(l.id) AS wl
        FROM SeriesJpaEntity s
        LEFT JOIN LikeJpaEntity l
               ON l.targetId = s.id
              AND l.targetType = :likeType
              AND l.createdAt >= :since AND l.createdAt < :until
        WHERE s.type = :seriesType
        GROUP BY s.id, s.title, s.thumbnailUrl
        HAVING (
            COUNT(l.id) < (
                SELECT COUNT(l2.id) FROM LikeJpaEntity l2
                WHERE l2.targetType = :likeType
                  AND l2.targetId   = :cursorId
                  AND l2.createdAt >= :since AND l2.createdAt < :until
            )
            OR (
                COUNT(l.id) = (
                    SELECT COUNT(l2.id) FROM LikeJpaEntity l2
                    WHERE l2.targetType = :likeType
                      AND l2.targetId   = :cursorId
                      AND l2.createdAt >= :since AND l2.createdAt < :until
                )
                AND s.id < :cursorId
            )
        )
        ORDER BY wl DESC, s.id DESC
    """)
    List<Object[]> weeklyPopularAfter(@Param("seriesType") Type seriesType,
                                      @Param("likeType") TargetType likeType,
                                      @Param("since") LocalDateTime since,
                                      @Param("until") LocalDateTime until,
                                      @Param("cursorId") Long cursorId,
                                      Pageable pageable);
    // 최신: 첫 페이지 — createdAt DESC, id DESC
    @Query("""
            
    SELECT s.id, s.title, s.thumbnailUrl,
           SUM(CASE WHEN l.createdAt >= :since AND l.createdAt < :until THEN 1 ELSE 0 END) AS wl,
           s.createdAt
    FROM SeriesJpaEntity s
    LEFT JOIN LikeJpaEntity l
           ON l.targetId = s.id
          AND l.targetType = :likeType
    WHERE s.type = :seriesType
    GROUP BY s.id, s.title, s.thumbnailUrl, s.createdAt
    ORDER BY s.createdAt DESC, s.id DESC
""")
    List<Object[]> latestFirst(@Param("seriesType") Type seriesType,
                               @Param("likeType") TargetType likeType,
                               @Param("since") LocalDateTime since,
                               @Param("until") LocalDateTime until,
                               Pageable pageable);

    // 최신: 커서 이후 — createdAt DESC, id DESC (별칭 X, WHERE에서 seek)
    @Query("""
            
    SELECT s.id, s.title, s.thumbnailUrl,
           SUM(CASE WHEN l.createdAt >= :since AND l.createdAt < :until THEN 1 ELSE 0 END) AS wl,
           s.createdAt
    FROM SeriesJpaEntity s
    LEFT JOIN LikeJpaEntity l
           ON l.targetId = s.id
          AND l.targetType = :likeType
    WHERE s.type = :seriesType
      AND (
            s.createdAt <  (SELECT s2.createdAt FROM SeriesJpaEntity s2 WHERE s2.id = :cursorId)
         OR (s.createdAt = (SELECT s2.createdAt FROM SeriesJpaEntity s2 WHERE s2.id = :cursorId)
             AND s.id < :cursorId)
      )
    GROUP BY s.id, s.title, s.thumbnailUrl, s.createdAt
    ORDER BY s.createdAt DESC, s.id DESC
""")
    List<Object[]> latestAfter(@Param("seriesType") Type seriesType,
                               @Param("likeType") TargetType likeType,
                               @Param("since") LocalDateTime since,
                               @Param("until") LocalDateTime until,
                               @Param("cursorId") Long cursorId,
                               Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE SeriesJpaEntity s
        SET s.seriesStatus = :to, s.updatedAt = CURRENT_TIMESTAMP
        WHERE s.id = :seriesId AND s.seriesStatus = :from
    """)
    int updateStatusIf(@Param("seriesId") Long seriesId,
                       @Param("from") SeriesStatus from,
                       @Param("to") SeriesStatus to);
}
