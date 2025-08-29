package com.teamflow.forestory_be.article.infrastructure.persistence;

import com.teamflow.forestory_be.article.domain.entity.Article;
import com.teamflow.forestory_be.article.domain.exception.ArticleNotFoundException;
import com.teamflow.forestory_be.article.domain.repository.ArticleRepositoryPort;
import com.teamflow.forestory_be.article.infrastructure.persistence.entity.ArticleJpaEntity;
import com.teamflow.forestory_be.article.infrastructure.persistence.repository.ArticleJpaRepository;
import com.teamflow.forestory_be.likes.domain.vo.TargetType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArticlePersistenceAdaptor implements ArticleRepositoryPort {

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public void save(Article article) {
        ArticleJpaEntity articleJpaEntity = ArticlePersistenceMapper.toJpaEntity(article);
        articleJpaRepository.save(articleJpaEntity);
    }

    @Override
    public Article getById(Long id) {
        ArticleJpaEntity articleJpaEntity = articleJpaRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        return ArticlePersistenceMapper.toDomainEntity(articleJpaEntity);
    }

    @Override
    public void deleteById(Long id) {
        articleJpaRepository.deleteById(id);
    }

    @Override
    public List<Article> findRandomPublishedByAuthor(Long authorId, Long excludeArticleId, int limit) {
        return articleJpaRepository.pickRandomByAuthor(authorId, excludeArticleId, limit)
                .stream()
                .map(ArticlePersistenceMapper::toDomainEntity)
                .toList();
    }

    @Transactional
    @Override
    public void increaseLikeCount(Long articleId) {
        int updated = articleJpaRepository.increaseLikeCount(articleId);
        if (updated == 0) {
            throw new ArticleNotFoundException();
        }
    }

    @Transactional
    @Override
    public void decreaseLikeCount(Long articleId) {
        int updated = articleJpaRepository.decreaseLikeCount(articleId);
        if (updated == 0) {
            throw new ArticleNotFoundException();
        }
    }

    @Override
    @Transactional
    public int deleteArticles(Long userId, Collection<Long> targetIds) {
        if (targetIds == null || targetIds.isEmpty())
            return 0;
        return articleJpaRepository.deleteArticles(userId, targetIds);
    }

    @Override
    public Map<Long, Article> findByIdsAsMap(List<Long> ids) {
        if (ids == null || ids.isEmpty())
            return Map.of();
        return articleJpaRepository.findAllByIdIn(ids).stream()
                .collect(Collectors.toMap(
                        ArticleJpaEntity::getId,
                        ArticlePersistenceMapper::toDomainEntity
                ));
    }

    /* ========= 추가: 홈 섹션 & 탭 목록용 ========= */

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findTopByWeeklyLikesRaw(int limit, LocalDateTime since, LocalDateTime until) {
        return articleJpaRepository.topByWeeklyLikes(
                since,
                until,
                PageRequest.of(0, Math.max(1, limit))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findWeeklyPopularAfterCursorById(int size, LocalDateTime since, LocalDateTime until,
                                                           Long cursorId) {
        Pageable p = PageRequest.of(0, Math.max(1, size));
        if (cursorId == null) {
            return articleJpaRepository.weeklyPopularFirst(since, until, p);
        }
        return articleJpaRepository.weeklyPopularAfter(since, until, cursorId, p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findLatestAfterCursorById(int size, LocalDateTime since, LocalDateTime until, Long cursorId) {
        Pageable p = PageRequest.of(0, Math.max(1, size));
        if (cursorId == null) {
            return articleJpaRepository.latestFirst(since, until, p);
        }
        return articleJpaRepository.latestAfter(since, until, cursorId, p);
    }
}