package com.teamflow.forestory_be.article.infrastructure.persistence.repository;

import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
