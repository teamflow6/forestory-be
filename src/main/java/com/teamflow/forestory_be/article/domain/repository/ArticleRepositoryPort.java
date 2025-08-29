package com.teamflow.forestory_be.article.domain.repository;

import com.teamflow.forestory_be.article.domain.entity.Article;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ArticleRepositoryPort {

    void save(Article article);

    Article getById(Long id);

    void deleteById(Long id);

    List<Article> findRandomPublishedByAuthor(Long authorId, Long excludeArticleId, int limit);

    void increaseLikeCount(Long articleId);

    void decreaseLikeCount(Long articleId);

    int deleteArticles(Long userId, Collection<Long> targetIds);

    Map<Long, Article> findByIdsAsMap(List<Long> ids);

    List<Object[]> findTopByWeeklyLikesRaw(int limit, LocalDateTime since, LocalDateTime until);

    /** 탭 목록(인기): 커서Id 이후 N개 (정렬: weeklyLikes DESC, id DESC) */
    List<Object[]> findWeeklyPopularAfterCursorById(
            int size, LocalDateTime since, LocalDateTime until, Long cursorId);

    /** 탭 목록(최신): 커서Id 이후 N개 (정렬: createdAt DESC, id DESC) */
    List<Object[]> findLatestAfterCursorById(
            int size, LocalDateTime since, LocalDateTime until, Long cursorId);
}
