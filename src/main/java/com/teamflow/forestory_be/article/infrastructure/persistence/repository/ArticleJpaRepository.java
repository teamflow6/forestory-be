package com.teamflow.forestory_be.article.infrastructure.persistence.repository;

import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import java.util.List;
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
}
