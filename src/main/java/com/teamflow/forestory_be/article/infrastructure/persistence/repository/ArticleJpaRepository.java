package com.teamflow.forestory_be.article.infrastructure.persistence.repository;

import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleJpaRepository extends JpaRepository<ArticleJpaEntity, Long> {
    @Query(value = """
        SELECT * FROM article
        WHERE author_id = :authorId
          AND status = 'PUBLISHED'
          AND (:excludeId IS NULL OR article.article_id <> :excludeId)
        ORDER BY RAND()
        LIMIT :limit
        """, nativeQuery = true)
    List<ArticleJpaEntity> pickRandomByAuthor(Long authorId, Long excludeId, int limit);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update ArticleJpaEntity a
              set a.likeCount = a.likeCount + 1
            where a.id = :id
           """)
    int increaseLikeCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update ArticleJpaEntity a
              set a.likeCount = case when a.likeCount > 0 then a.likeCount - 1 else 0 end
            where a.id = :id
           """)
    int decreaseLikeCount(@Param("id") Long id);

    @Query("""
    select a
    from ArticleJpaEntity a
    where a.authorId = :authorId
      and a.status = com.teamflow.forestory_be.article.domain.vo.ArticleStatus.DRAFT
      and (:cursor is null or a.createdAt < :cursor)
    order by a.createdAt desc
    """)
    List<ArticleJpaEntity> sliceArticle(
            @Param("authorId") Long authorId,
            @Param("cursor") LocalDateTime cursor,
            Pageable pageable
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        DELETE FROM ArticleJpaEntity a
        WHERE a.authorId = :userId
          AND a.id IN :targetIds
    """)
    int deleteArticles(Long userId, Collection<Long> targetIds);


    @Query("SELECT a FROM ArticleJpaEntity a WHERE a.id IN :ids")
    List<ArticleJpaEntity> findAllByIdIn(@Param("ids") List<Long> ids);

    @Query("""
        SELECT a.id, a.title, a.thumbnailUrl, COUNT(l.id)
        FROM ArticleJpaEntity a
        LEFT JOIN LikeJpaEntity l
               ON l.targetId = a.id
              AND l.targetType = 'ARTICLE'
              AND l.createdAt >= :since
              AND l.createdAt <  :until
        GROUP BY a.id, a.title, a.thumbnailUrl
        ORDER BY COUNT(l.id) DESC, a.id DESC
    """)
    List<Object[]> topByWeeklyLikes(@Param("since") LocalDateTime since,
                                    @Param("until") LocalDateTime until,
                                    Pageable pageable);

    // 인기(첫 페이지)
    @Query("""
        SELECT a.id, a.title, a.thumbnailUrl, COUNT(l.id) AS wl
        FROM ArticleJpaEntity a
        LEFT JOIN LikeJpaEntity l
               ON l.targetId = a.id
              AND l.targetType = 'ARTICLE'
              AND l.createdAt >= :since
              AND l.createdAt <  :until
        GROUP BY a.id, a.title, a.thumbnailUrl
        ORDER BY wl DESC, a.id DESC
    """)
    List<Object[]> weeklyPopularFirst(@Param("since") LocalDateTime since,
                                      @Param("until") LocalDateTime until,
                                      Pageable pageable);

    // 인기(커서 이후)
    @Query("""
        SELECT a.id, a.title, a.thumbnailUrl, COUNT(l.id) AS wl
        FROM ArticleJpaEntity a
        LEFT JOIN LikeJpaEntity l
               ON l.targetId = a.id
              AND l.targetType = 'ARTICLE'
              AND l.createdAt >= :since
              AND l.createdAt <  :until
        GROUP BY a.id, a.title, a.thumbnailUrl
        HAVING (
            COUNT(l.id) < (
                SELECT COUNT(l2.id) FROM LikeJpaEntity l2
                WHERE l2.targetType = 'ARTICLE'
                  AND l2.targetId   = :cursorId
                  AND l2.createdAt >= :since
                  AND l2.createdAt <  :until
            )
            OR (
                COUNT(l.id) = (
                    SELECT COUNT(l2.id) FROM LikeJpaEntity l2
                    WHERE l2.targetType = 'ARTICLE'
                      AND l2.targetId   = :cursorId
                      AND l2.createdAt >= :since
                      AND l2.createdAt <  :until
                )
                AND a.id < :cursorId
            )
        )
        ORDER BY wl DESC, a.id DESC
    """)
    List<Object[]> weeklyPopularAfter(@Param("since") LocalDateTime since,
                                      @Param("until") LocalDateTime until,
                                      @Param("cursorId") Long cursorId,
                                      Pageable pageable);

    // 최신(첫 페이지) — createdAt DESC, id DESC
    @Query("""
    SELECT a.id, a.title, a.thumbnailUrl,
           SUM(CASE WHEN l.createdAt >= :since AND l.createdAt < :until THEN 1 ELSE 0 END) AS wl,
           a.createdAt
    FROM ArticleJpaEntity a
    LEFT JOIN LikeJpaEntity l ON l.targetId = a.id AND l.targetType = 'ARTICLE'
    GROUP BY a.id, a.title, a.thumbnailUrl, a.createdAt
    ORDER BY a.createdAt DESC, a.id DESC
""")
    List<Object[]> latestFirst(@Param("since") LocalDateTime since,
                               @Param("until") LocalDateTime until,
                               Pageable pageable);

    // 최신(커서 이후) — createdAt DESC, id DESC
    @Query("""
    SELECT a.id, a.title, a.thumbnailUrl,
           SUM(CASE WHEN l.createdAt >= :since AND l.createdAt < :until THEN 1 ELSE 0 END) AS wl,
           a.createdAt
    FROM ArticleJpaEntity a
    LEFT JOIN LikeJpaEntity l ON l.targetId = a.id AND l.targetType = 'ARTICLE'
    WHERE
         a.createdAt <  (SELECT a2.createdAt FROM ArticleJpaEntity a2 WHERE a2.id = :cursorId)
      OR (a.createdAt = (SELECT a2.createdAt FROM ArticleJpaEntity a2 WHERE a2.id = :cursorId)
          AND a.id < :cursorId)
    GROUP BY a.id, a.title, a.thumbnailUrl, a.createdAt
    ORDER BY a.createdAt DESC, a.id DESC
""")
    List<Object[]> latestAfter(@Param("since") LocalDateTime since,
                               @Param("until") LocalDateTime until,
                               @Param("cursorId") Long cursorId,
                               Pageable pageable);


}
