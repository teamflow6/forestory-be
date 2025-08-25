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

}
